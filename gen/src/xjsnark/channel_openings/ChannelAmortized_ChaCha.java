package xjsnark.channel_openings;

/*Generated by MPS */

import backend.structure.CircuitGenerator;
import backend.config.Config;
import backend.eval.SampleRun;
import java.math.BigInteger;
import backend.auxTypes.FieldElement;
import backend.auxTypes.UnsignedInteger;
import util.Util;
import xjsnark.poseidon.PoseidonHash;
import xjsnark.chacha.ChaCha;
import backend.eval.CircuitEvaluator;

public class ChannelAmortized_ChaCha extends CircuitGenerator {



  public static void main(String[] args) {
    Config.arithOptimizerNumThreads = 8;
    Config.multivariateExpressionMinimization = true;

    Config.writeCircuits = true;
    Config.outputFilesPath = "./circuits";

    new ChannelAmortized_ChaCha();
  }

  public ChannelAmortized_ChaCha() {
    super("ChannelAmortized_ChaCha");
    __generateCircuit();
    this.__evaluateSampleRun(new SampleRun("Sample_Run1", true) {
      public void pre() {
        try {

          // Example commitment string: 1b232aaea72bc6357b33018f4c83970e8a25a2ee5ae6f4b58f638cb87caeb4ea 
          String comm_str = "1b232aaea72bc6357b33018f4c83970e8a25a2ee5ae6f4b58f638cb87caeb4ea";

          // Key, iv that were committed to 
          String key_str = "1c9240a5eb55d38af333888604f6b5f0473917c1402b80099dca5cbc207075c0";
          String iv_str = "000000000000000000000002";

          // query is amazon.com: 001ce8410100000100000000000006616d617a6f6e03636f6d0000010001 
          // 001aa41f010000010000000000000477696b6903636f6d0000010001 
          // 001ce8410100000100000000000006616d617a6f6e03636f6d0000010001 
          String dns_ct_str = "001c60d10100000100000000000006616d617a6f6e03636f6d0000010001";

          // Convert the strings to circuit input types 

          comm.mapValue(new BigInteger(comm_str, 16), CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());

          for (int i = 0; i < key_str.length() / 2; i = i + 1) {
            key[i].mapValue(new BigInteger(key_str.substring(2 * i, 2 * i + 2), 16), CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());
          }

          for (int i = 0; i < iv_str.length() / 2; i = i + 1) {
            nonce[i].mapValue(new BigInteger(iv_str.substring(2 * i, 2 * i + 2), 16), CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());
          }

          SN.mapValue(BigInteger.ZERO, CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());

          for (int i = 0; i < dns_ct_str.length() / 2; i = i + 1) {
            appl_ct[i].mapValue(new BigInteger(dns_ct_str.substring(2 * i, 2 * i + 2), 16), CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());
          }
          for (int i = dns_ct_str.length() / 2; i < 255; i = i + 1) {
            appl_ct[i].mapValue(new BigInteger("0", 16), CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());
          }

        } catch (Exception ex) {
          System.out.println("Error: Issue with entering inputs.");
        }



      }
      public void post() {
        System.out.println("Circuit Output: ");
        for (int i = 0; i < output.length; i++) {
          System.out.print(String.format("%1$02x", output[i].getValueFromEvaluator(CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator())));
        }
        System.out.println("\n");


        System.out.print(output_Fp.getValueFromEvaluator(CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator()).toString(16));

      }

    });

  }



  public void __init() {
    comm = new FieldElement(new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617"), new BigInteger("0"));
    SN = new UnsignedInteger(64, new BigInteger("0"));
    appl_ct = (UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{255}, 8);
    key = (UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{32}, 8);
    nonce = (UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{12}, 8);
    output = (UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{32}, 8);
    output_Fp = new FieldElement(new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617"), new BigInteger("0"));
  }

  public FieldElement comm;
  public UnsignedInteger SN;
  public UnsignedInteger[] appl_ct;
  public UnsignedInteger[] key;
  public UnsignedInteger[] nonce;
  public UnsignedInteger[] output;
  public FieldElement output_Fp;

  @Override
  public void __defineInputs() {
    super.__defineInputs();
    SN = UnsignedInteger.createInput(this, 64);
    comm = FieldElement.createInput(this, new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617"));



    appl_ct = (UnsignedInteger[]) UnsignedInteger.createInputArray(CircuitGenerator.__getActiveCircuitGenerator(), Util.getArrayDimensions(appl_ct), 8);












  }
  @Override
  public void __defineOutputs() {
    super.__defineOutputs();









  }
  @Override
  public void __defineVerifiedWitnesses() {
    super.__defineVerifiedWitnesses();




    key = (UnsignedInteger[]) UnsignedInteger.createVerifiedWitnessArray(CircuitGenerator.__getActiveCircuitGenerator(), Util.getArrayDimensions(key), 8);
    nonce = (UnsignedInteger[]) UnsignedInteger.createVerifiedWitnessArray(CircuitGenerator.__getActiveCircuitGenerator(), Util.getArrayDimensions(nonce), 8);















  }
  @Override
  public void __defineWitnesses() {
    super.__defineWitnesses();

















  }
  public void outsource() {

    // Compute the commitment, which is the Poseidon hash of key || iv 
    UnsignedInteger[] concat = xjsnark.util_and_sha.Util.concat(key, nonce);
    UnsignedInteger[] concat_256 = xjsnark.util_and_sha.Util.convert_8_to_256_array(xjsnark.util_and_sha.Util.concat((UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{20}, 8), concat));
    FieldElement comm_cal = PoseidonHash.poseidon_hash(new FieldElement[]{FieldElement.instantiateFrom(new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617"), concat_256[0]).copy(), FieldElement.instantiateFrom(new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617"), concat_256[1]).copy()}).copy();

    // Verify that the two commitments are equal 
    comm.forceEqual(comm_cal);

    // Compute iv xor (0^32 || SN)  
    // this acts as the iv for this TLS record 


    // Use the input keys to decrypt at the specified block 

    output = ChaCha.chachaEncrypt(key, nonce, appl_ct, UnsignedInteger.instantiateFrom(32, 42).copy(32));

    // For logging output 
    output_Fp.assign(comm_cal);

  }

  public void __generateSampleInput(CircuitEvaluator evaluator) {
    __generateRandomInput(evaluator);
  }

}