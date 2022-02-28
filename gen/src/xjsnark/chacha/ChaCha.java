package xjsnark.chacha;

/*Generated by MPS */

import backend.auxTypes.UnsignedInteger;
import xjsnark.util_and_sha.Util;
import backend.structure.CircuitGenerator;

public class ChaCha {

  public static int CHACHA20_OUTPUT_SIZE = 64;


  public static void quarter_round(UnsignedInteger[][] state, int ind1, int ind2, int ind3, int ind4) {
    UnsignedInteger a = state[ind1 / 4][ind1 % 4].copy(32);
    UnsignedInteger b = state[ind2 / 4][ind2 % 4].copy(32);
    UnsignedInteger c = state[ind3 / 4][ind3 % 4].copy(32);
    UnsignedInteger d = state[ind4 / 4][ind4 % 4].copy(32);

    a.assign(a.add(b), 32);
    d.assign(d.xorBitwise(a), 32);
    d.assign(ROTL(d.copy(32), 16), 32);

    c.assign(c.add(d), 32);
    b.assign(b.xorBitwise(c), 32);
    b.assign(ROTL(b.copy(32), 12), 32);

    a.assign(a.add(b), 32);
    d.assign(d.xorBitwise(a), 32);
    d.assign(ROTL(d.copy(32), 8), 32);

    c.assign(c.add(d), 32);
    b.assign(b.xorBitwise(c), 32);
    b.assign(ROTL(b.copy(32), 7), 32);

    state[ind1 / 4][ind1 % 4].assign(a, 32);
    state[ind2 / 4][ind2 % 4].assign(b, 32);
    state[ind3 / 4][ind3 % 4].assign(c, 32);
    state[ind4 / 4][ind4 % 4].assign(d, 32);

  }

  public static UnsignedInteger[] chacha20block(UnsignedInteger[] key, UnsignedInteger[] nonce, UnsignedInteger count) {

    UnsignedInteger[] key32 = Util.convert_8_to_32(key);

    UnsignedInteger[] nonce32 = Util.convert_8_to_32(nonce);

    return Util.convert_32_to_8(chacha20block32(key32, nonce32, count.copy(32)));
  }


  public static UnsignedInteger[] chacha20block32(UnsignedInteger[] key32, UnsignedInteger[] nonce32, UnsignedInteger count) {

    // it's not actually a ciphertext, nor is this function invertible. 
    UnsignedInteger[] keystream = (UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{CHACHA20_OUTPUT_SIZE / 4}, 32);

    UnsignedInteger[][] state = (UnsignedInteger[][]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{4, 4}, 32);
    state[0][0].assign(UnsignedInteger.instantiateFrom(32, 0x61707865), 32);
    state[0][1].assign(UnsignedInteger.instantiateFrom(32, 0x3320646e), 32);
    state[0][2].assign(UnsignedInteger.instantiateFrom(32, 0x79622d32), 32);
    state[0][3].assign(UnsignedInteger.instantiateFrom(32, 0x6b206574), 32);
    for (int i = 1; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        state[i][j].assign(Util.rev_bytes_32(key32[4 * (i - 1) + j].copy(32)), 32);
      }
    }
    state[3][0].assign(count, 32);

    for (int j = 0; j < 3; j++) {
      state[3][j + 1].assign(Util.rev_bytes_32(nonce32[j].copy(32)), 32);
    }

    UnsignedInteger[][] INIT_state = (UnsignedInteger[][]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{4, 4}, 32);
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        INIT_state[i][j].assign(state[i][j], 32);
      }
    }

    int numrounds = 20;
    for (int i = 0; i < numrounds; i += 2) {
      quarter_round(state, 0, 4, 8, 12);
      quarter_round(state, 1, 5, 9, 13);
      quarter_round(state, 2, 6, 10, 14);
      quarter_round(state, 3, 7, 11, 15);

      quarter_round(state, 0, 5, 10, 15);
      quarter_round(state, 1, 6, 11, 12);
      quarter_round(state, 2, 7, 8, 13);
      quarter_round(state, 3, 4, 9, 14);
    }
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        keystream[4 * i + j].assign(state[i][j].add(INIT_state[i][j]), 32);
      }
    }

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        keystream[4 * i + j].assign(Util.rev_bytes_32(keystream[4 * i + j].copy(32)), 32);
      }
    }


    return keystream;
  }
  public static UnsignedInteger ROTL(UnsignedInteger in, int dist) {
    return (in.shiftLeft(dist)).orBitwise((in.shiftRight((32 - dist))));
  }

  public static UnsignedInteger[] chachaEncrypt(UnsignedInteger[] key, UnsignedInteger[] nonce, UnsignedInteger[] msg, UnsignedInteger counter) {

    // one ChaCha block is 512 bits = 64 bytes 
    int num_blocks = (msg.length / 64) + 1;

    UnsignedInteger[] full_stream = (UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{0}, 8);

    for (int i = 0; i < num_blocks; i++) {



      UnsignedInteger[] block_stream = chacha20block(key, nonce, UnsignedInteger.instantiateFrom(32, i).add(counter).copy(32));

      full_stream = Util.concat(full_stream, block_stream);

    }

    return Util.xor_arrays_prefix(msg, full_stream, msg.length);

  }

}
