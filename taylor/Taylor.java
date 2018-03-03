import java.util.Random;
import java.util.Scanner;

public class Taylor {

  public static double getMachineEpsilon(double EPS) {
    double prevEpsilon = 0.0;
    while ((1 + EPS) != 1) {
      prevEpsilon = EPS;
      EPS /= 2;
    }
    return prevEpsilon;
  }

  public static double factorial (double num) {
    double factorial = 1;
    for (double i = num; i > 1; i--) {
      factorial *= i;
    }
    return factorial;
  }

  public static double[] nFactorial (double num) {
    double factoriales[] = new double[(int)num];
    for (double i = num; i >= 0; i--) {
      factoriales[(int)i] = factorial(i);
    }
    return factoriales;
  }

  public static double[] coefSen (int num, int c) {
    double coeficientes[] = new double[num];
    for (int i = 0; i < num; i++) {
      if (i % 2 == 0)
        coeficientes[i] = Math.sin(c)/factorial((double)i);
      else
        coeficientes[i] = Math.cos(c)/factorial((double)i);
    }
    return coeficientes;
  }

  public static double horner (double polinomio[], double x, int c) {
    int grado = polinomio.length - 1;
    double suma = polinomio[grado];
    for (int i = grado - 1; i >= 0; i--)
      suma = ((x - c) * suma) + polinomio[i];
    return suma;
  }

  /*public static double falsaPosicion () {
    double result;
    return result;
  }*/

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.print("Centrar la serie en: ");
    int c = scan.nextInt();
    System.out.print("Sumandos de la serie: ");
    int sumandos = scan.nextInt();
    double[] coeficientes = coefSen(sumandos, c);
    System.out.print("Valor de x: ");
    double x = scan.nextDouble();
    System.out.println(horner(coeficientes, x, c));
  }
}
