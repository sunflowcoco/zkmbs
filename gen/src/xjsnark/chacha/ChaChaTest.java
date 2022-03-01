package xjsnark.chacha;

/*Generated by MPS */

import backend.structure.CircuitGenerator;
import backend.eval.SampleRun;
import java.math.BigInteger;
import backend.auxTypes.UnsignedInteger;
import util.Util;
import backend.eval.CircuitEvaluator;

public class ChaChaTest extends CircuitGenerator {



  public static void main(String[] args) {
    // This is the java main method. Its purpose is to make the Progam runnable in the environment 
    // This method can be left empty, or used to set Configuration params (see examples) 
    new ChaChaTest();
  }

  public ChaChaTest() {
    super("ChaChaTest");
    __generateCircuit();
    this.__evaluateSampleRun(new SampleRun("Sample_Run1", true) {
      public void pre() {
        String dns_ct_line = "001ce8410100000100000000000006616d617a6f6e03636f6d0000010001";

        for (int i = 0; i < dns_ct_line.length() / 2; i = i + 1) {
          ct[i].mapValue(new BigInteger(dns_ct_line.substring(2 * i, 2 * i + 2), 16), CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());
        }
        for (int i = dns_ct_line.length() / 2; i < CT_LENGTH; i = i + 1) {
          ct[i].mapValue(new BigInteger("0"), CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());
        }

        String key_string = "1c9240a5eb55d38af333888604f6b5f0473917c1402b80099dca5cbc207075c0";
        String nonce_string = "000000000000000000000002";

        for (int i = 0; i < key_string.length() / 2; i = i + 1) {
          key[i].mapValue(new BigInteger(key_string.substring(2 * i, 2 * i + 2), 16), CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());
        }

        for (int i = 0; i < nonce_string.length() / 2; i = i + 1) {
          nonce[i].mapValue(new BigInteger(nonce_string.substring(2 * i, 2 * i + 2), 16), CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator());
        }


      }
      public void post() {
        System.out.println("Circuit Output: ");
        for (int i = 0; i < pt.length; i++) {
          System.out.print(String.format("%1$02x", pt[i].getValueFromEvaluator(CircuitGenerator.__getActiveCircuitGenerator().__getCircuitEvaluator())));
        }
        System.out.println("\n");

      }

    });

  }



  public void __init() {
    key = (UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{32}, 8);
    nonce = (UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{12}, 8);
    ct = (UnsignedInteger[]) UnsignedInteger.createZeroArray(CircuitGenerator.__getActiveCircuitGenerator(), new int[]{CT_LENGTH}, 8);
  }

  public UnsignedInteger[] key;
  public UnsignedInteger[] nonce;
  public UnsignedInteger[] pt;
  public UnsignedInteger[] ct;

  public static int CT_LENGTH = 255;
  @Override
  public void __defineInputs() {
    super.__defineInputs();



    key = (UnsignedInteger[]) UnsignedInteger.createInputArray(CircuitGenerator.__getActiveCircuitGenerator(), Util.getArrayDimensions(key), 8);
    nonce = (UnsignedInteger[]) UnsignedInteger.createInputArray(CircuitGenerator.__getActiveCircuitGenerator(), Util.getArrayDimensions(nonce), 8);












  }
  @Override
  public void __defineOutputs() {
    super.__defineOutputs();









  }
  @Override
  public void __defineVerifiedWitnesses() {
    super.__defineVerifiedWitnesses();




    ct = (UnsignedInteger[]) UnsignedInteger.createVerifiedWitnessArray(CircuitGenerator.__getActiveCircuitGenerator(), Util.getArrayDimensions(ct), 8);















  }
  @Override
  public void __defineWitnesses() {
    super.__defineWitnesses();

















  }
  public void outsource() {
    // Entry point for the circuit. Input and witness arrays/structs must be instantiated outside this method 

    pt = ChaCha.chachaEncrypt(key, nonce, ct, UnsignedInteger.instantiateFrom(32, 42).copy(32));
  }

  public void __generateSampleInput(CircuitEvaluator evaluator) {
    __generateRandomInput(evaluator);
  }

}