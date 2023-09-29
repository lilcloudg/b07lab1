import java.util.*;
import java.util.stream.IntStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        this.coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial add(Polynomial p) {
        int[] newExponents = new int[exponents.length];
        double[] newCoefficients = new double[coefficients.length];

        for (int i = 0; i < exponents.length; i++) {
            newExponents[i] = exponents[i];
            newCoefficients[i] = coefficients[i];
        }

        for (int i = 0; i < p.exponents.length; i++) {
            
			boolean found = false;
            for (int j = 0; j < newExponents.length; j++) {
                if (p.exponents[i] == newExponents[j]) {
                    newCoefficients[j] += p.coefficients[i];
                    found = true;
                    break;
                }
            }
            if (!found) {
                newExponents = Arrays.copyOf(newExponents, newExponents.length + 1);
                newCoefficients = Arrays.copyOf(newCoefficients, newCoefficients.length + 1);
                newExponents[newExponents.length - 1] = p.exponents[i];
                newCoefficients[newCoefficients.length - 1] = p.coefficients[i];
            }
        }

        final double[] finalNewCoefficients = new double[newCoefficients.length];
        final int[] finalNewExponents = new int[newExponents.length];
        System.arraycopy(newCoefficients, 0, finalNewCoefficients, 0, newCoefficients.length);
        System.arraycopy(newExponents, 0, finalNewExponents, 0, newExponents.length);

        for (int i = 0; i < newCoefficients.length; i++) {
            if (newCoefficients[i] == 0) {
                int index = 0;
                newCoefficients = IntStream.range(0, finalNewCoefficients.length)
                        .filter(j -> j != index)
                        .mapToDouble(j -> finalNewCoefficients[j])
                        .toArray();

                newExponents = IntStream.range(0, finalNewExponents.length)
                        .filter(j -> j != index)
                        .map(j -> finalNewExponents[j])
                        .toArray();
            }
        }

        return new Polynomial(newCoefficients, newExponents);
    }

    public Polynomial multiply(Polynomial p) {
        int[] newExponents = new int[exponents.length * p.exponents.length];
        double[] newCoefficients = new double[coefficients.length * p.coefficients.length];
        int index = 0;

        for (int i = 0; i < exponents.length; i++) {
            for (int j = 0; j < p.exponents.length; j++) {
                newExponents[index] = exponents[i] + p.exponents[j];
                newCoefficients[index] = coefficients[i] * p.coefficients[j];
                index++;
            }
        }

        for (int i = 0; i < newCoefficients.length; i++) {
            for (int j = i + 1; j < newCoefficients.length; j++) {
                if (newExponents[i] == newExponents[j]) {
                    newCoefficients[i] += newCoefficients[j];
                    newCoefficients[j] = 0;
                }
            }
        }

        final double[] finalNewCoefficients = new double[newCoefficients.length];
        final int[] finalNewExponents = new int[newExponents.length];
        System.arraycopy(newCoefficients, 0, finalNewCoefficients, 0, newCoefficients.length);
        System.arraycopy(newExponents, 0, finalNewExponents, 0, newExponents.length);

        for (int i = 0; i < newCoefficients.length; i++) {
            if (newCoefficients[i] == 0) {
                int index2 = i;
                newCoefficients = IntStream.range(0, finalNewCoefficients.length)
                        .filter(j -> j != index2)
                        .mapToDouble(j -> finalNewCoefficients[j])
                        .toArray();

                newExponents = IntStream.range(0, finalNewExponents.length)
                        .filter(j -> j != index2)
                        .map(j -> finalNewExponents[j])
                        .toArray();
            }
        }

        return new Polynomial(newCoefficients, newExponents);
    }
	

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public Polynomial(File file) throws FileNotFoundException {
        
        String[] terms;

        try (Scanner scanner = new Scanner(file)){
            String polynomial = scanner.nextLine();
            terms = polynomial.split("(?=[-+])");
        }

        coefficients = new double[terms.length];
        exponents = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {
            String[] parts = terms[i].split("x");
            coefficients[i] = Double.parseDouble(parts[0]);
            if (parts.length > 1) {
                exponents[i] = Integer.parseInt(parts[1].substring(1));
            } else {
                exponents[i] = 0;
            }
        }
    }

    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            for (int i = 0; i < coefficients.length; i++) {
                if (i > 0 && coefficients[i] >= 0) {
                    writer.print("+");
                }
                writer.print(coefficients[i] + "x^" + exponents[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
