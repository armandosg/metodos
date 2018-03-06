import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;

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

  public static double[] coefSen (int num, double c) {
    double coeficientes[] = new double[num];
    for (int i = 0; i < num; i++) {
      switch ((i + 1) % 4) {
        case 1: coeficientes[i] = Math.sin(c)/factorial((double)i);
          break;
        case 2: coeficientes[i] = Math.cos(c)/factorial((double)i);
          break;
        case 3: coeficientes[i] = (-1 * Math.sin(c))/factorial((double)i);
          break;
        case 0: coeficientes[i] = (-1 * Math.cos(c))/factorial((double)i);
          break;
      }
    }
    return coeficientes;
  }

  public static double horner (double polinomio[], double x, double c) {
    int grado = polinomio.length - 1;
    double suma = polinomio[grado];
    for (int i = grado - 1; i >= 0; i--)
      suma = ((x - c) * suma) + polinomio[i];
    return suma;
  }

  public static double falsaPosicion (double polinomio[], double x0, double xf, double c) {
    double f0, ff, fr = 0, xr = 0, xra = 0;
    for (int i = 0; xr != xra || i == 0; i ++) {
      xra = xr;
      f0 = horner(polinomio, x0, c);
      ff = horner(polinomio, xf, c);
      xr = xf - ((ff * (x0 - xf)) / (f0 - ff));
      fr = horner(polinomio, xr, c);
      if (fr * f0 < 0)
        xf = xr;
      else if (fr * f0 > 0)
        x0 = xr;
      //String f = String.format("f0 = %.18f, ff = %.18f, fr = %.18f, xf = %.18f, x0 = %.18f, xr = %.18f", f0, ff, fr, xf, x0, xr);
      System.out.println(String.format("xr = %.18f", xr));
      System.out.println(String.format("xra = %.18f", xr));
    }
    return xr;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.print("Centrar la serie en: ");
    double c = scan.nextDouble();

    System.out.print("Sumandos de la serie: ");
    int sumandos = scan.nextInt();
    double[] coeficientes = coefSen(sumandos, c);

    System.out.print("Valor de x0: ");
    double x0 = scan.nextDouble();

    System.out.print("Valor de xf: ");
    double xf = scan.nextDouble();

    /*System.out.print("Valor de la tolerancia: ");
    double tolerancia = scan.nextDouble();*/

    double resultado = falsaPosicion(coeficientes, x0, xf, c);
    System.out.println(resultado);
  }
}
