import java.util.Random;
import java.util.Scanner;
import java.math.BigDecimal;

public class Taylor {

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


  public static double aumentar (int numCoef, double c, double tolerancia) {
    double error, piaprox;
    double[] polinomio;
    do {
      polinomio = coefSen(numCoef, c);
      piaprox = falsaPosicion(polinomio, c, tolerancia);
      error = Math.abs(((Math.PI - piaprox) / Math.PI) * 100);
      System.out.println("Sumandos");
      for (int i = 0; i < polinomio.length; i ++)
        System.out.println("\t" + polinomio[i] + "\tx^" + i);
      System.out.println("Error para " + polinomio.length + " sumandos: " + error + "\n");
      numCoef ++;
    } while (error >= tolerancia);
    return piaprox;
  }

  public static double falsaPosicion (double polinomio[], double c, double tolerancia) {
    double f0, ff, fr, error;
    double x0 = 3, xf = 3.5, xr = 0, xra;
    do {
      xra = xr;
      f0 = horner(polinomio, x0, c);
      ff = horner(polinomio, xf, c);
      xr = xf - ((ff * (x0 - xf)) / (f0 - ff));
      fr = horner(polinomio, xr, c);
      error = Math.abs( ( (xr - xra) / xr ) * 100 );
      if (fr * f0 < 0)
        xf = xr;
      else if (fr * f0 > 0)
        x0 = xr;
    } while(error >= tolerancia);
    return xr;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.print("Centrar la serie en: ");
    double c = scan.nextDouble();

    System.out.print("Sumandos de la serie: ");
    int sumandos = scan.nextInt();

    System.out.print("Cifras significativas: ");
    int cifras = scan.nextInt();
    double tolerancia = 0.5 * Math.pow(10, (2-cifras));

    double resultado = aumentar(sumandos, c, tolerancia);
    System.out.println("\nValor aprox. de PI: " + resultado);
    System.out.println("Valor real de PI: " + Math.PI);
  }
}
