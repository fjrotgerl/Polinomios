import java.security.cert.PolicyNode;
import java.util.Arrays;

public class Polynomial {
    float[] cfs = { 0 };
    String s = "ss";

    // Constructor per defecte. Genera un polinomi zero
    public Polynomial() {
    }

    // Constructor a partir dels coeficients del polinomi en forma d'array
    public Polynomial(float[] cfs) {
        this.cfs = cfs;
        System.out.println(Arrays.toString(this.cfs));
    }

    // Constructor a partir d'un string
    public Polynomial(String s) { this.s = s;
        float mult = 1;
        String[] ar_string = s.split(" ");
        for (String ss : ar_string) {
            if (ss.equals("+")) {
                mult = 1;
                continue;
            } if (ss.equals("-")) {
                mult = -1;
                continue;
            }
            int exponent = treuExponent(ss);
            float coeficient = treuCoeficient(ss)*mult;
            if (coeficient == 0) {
                continue;
            }
            setCoeficient(coeficient,exponent);
        }
        girar(this.cfs);
    }

    private void girar(float[] p) {
        for (int i = 0; i < this.cfs.length / 2; i++) {
            float aux = p[i];
            p[i] = p[p.length-i-1];
            p[p.length-i-1] = aux;
        }
    }

    private void setCoeficient(float coeficient, int exponent) {
        if (exponent >= this.cfs.length) {
            float[] aux = new float[exponent+1];
            aux[exponent] = coeficient;
            for (int i = 0; i < aux.length; i++) {
                if (i <= this.cfs.length-1) {
                    aux[i] = this.cfs[i];
                }
            }
            this.cfs = aux;
        } else {
            this.cfs[exponent] += coeficient;
        }
    }

    private float treuCoeficient(String cocient) {
        float co = 0;
        int c = 0;
        for (int i = 0; i < cocient.length(); i++) {
            if (cocient.charAt(i) != 'x') {
                c++;
            } if (cocient.charAt(i) == 'x') {
                break;
            }

        }
        if (cocient.contains("x")) {
            for (int i = 0; i < cocient.length(); i++) {
                if (cocient.charAt(i) == 'x' && i == 0) {
                    co = 1;
                } if (cocient.charAt(i) == '-' && cocient.charAt(i+1) == 'x') {
                    co = -1;
                    return co;
                } else if (cocient.charAt(i) != 'x') {
                    co = Float.parseFloat(cocient.substring(0,c));
                }
                return co;
            }
        } if (!cocient.contains("x")) {
            co = Float.parseFloat(cocient.substring(0));
        }
        return co;
    }

    private int treuExponent(String exponent) {
        int expo = 0;
        if (exponent.contains("x") && !exponent.contains("^")) {
            expo = 1;
            return expo;
        } if (exponent.contains("x") && exponent.contains("^")) {
            for (int i = 0; i < exponent.length(); i++) {
                if (exponent.charAt(i) == '^') {
                    expo = Integer.parseInt(exponent.substring(i + 1));
                }
            }
            return expo;
        }
        return expo;
    }

    // Suma el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial add(Polynomial p) {
        float[] ar;
        float[] ar2;
        float[] resultat;
        if (p.cfs.length > this.cfs.length) {
            ar = new float[p.cfs.length];
            ar2 = new float[p.cfs.length];
            resultat = new float[p.cfs.length];
        } else {
            ar = new float[this.cfs.length];
            ar2 = new float[this.cfs.length];
            resultat = new float[this.cfs.length];
        }
        int o = ar.length-p.cfs.length;
        for (int j = 0; j < p.cfs.length; j++) {
            ar[o] = p.cfs[j];
            o++;
        }
        int o2 = ar2.length-this.cfs.length;
        for (int i = 0; i < this.cfs.length; i++) {
            ar2[o2] = this.cfs[i];
            o2++;
        }
        for (int i = 0; i < ar.length; i++) {
            resultat[i] = ar2[i] + ar[i];
        }
        Polynomial result = new Polynomial(resultat);
        return result;
    }


    //pene
    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {
        float[] resultat = new float[this.cfs.length+p2.cfs.length-1];
        for (int i = 0; i < this.cfs.length; i++) {
            if (this.cfs[i] == 0) {
                continue;
            }
            for (int j = 0; j < p2.cfs.length; j++) {
                resultat[i+j] += this.cfs[i]*p2.cfs[j];
            }
        }
        Polynomial result = new Polynomial(resultat);
        return result;
    }

    // Divideix el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    // Torna el quocient i també el residu (ambdós polinomis)
    public Polynomial[] div(Polynomial p2) {
       return null;
    }

    // Troba les arrels del polinomi, ordenades de menor a major
    public float[] roots() {
        return null;
    }

    // Torna "true" si els polinomis són iguals. Això és un override d'un mètode de la classe Object
    @Override
    public boolean equals(Object o) {
        Polynomial p = (Polynomial) o;
        return this.toString().equals(p.toString());
    }

    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        int cont = cfs.length-1;
        StringBuilder sb = new StringBuilder();
        System.out.println(Arrays.toString(this.cfs));
        for (int i = 0; i < this.cfs.length; i++) {

            if (this.cfs.length == 1) {
                if (this.cfs[i] == 0) {
                    sb.append((int)this.cfs[i]);
                }
                else if (this.cfs[i] != 0) {
                    sb.append((int)this.cfs[i]);
                }
            }

            if (this.cfs.length == 2) {
                if (i == 0 && this.cfs[i] == 1 || this.cfs[i] == -1) {
                    if (i == 0 && this.cfs[i+1] == 0 && this.cfs[i] != -1) {
                        sb.append("x");
                        continue;
                    } if (i == 0 && this.cfs[i] == -1 && this.cfs[i+1] > 0) {
                        sb.append("-x + ");
                    } else if (i == 0 && this.cfs[i] == -1 && this.cfs[i+1] < 0) {
                        sb.append("-x - ");
                    } else {
                        sb.append("x + ");
                    }
                } else if (i == 0 && this.cfs[i] != 1) {
                    if (i == 0 && this.cfs[i+1] == 0 && this.cfs[i] != 0) {
                        sb.append((int)this.cfs[i] + "x");
                        continue;
                    } else if (this.cfs[i] == 0) {
                        sb.append("0");
                    } else {
                        sb.append((int) this.cfs[i] + "x + ");
                    }
                } else if (i == 1 && this.cfs[i] < 0) {
                    this.cfs[i] = this.cfs[i] * (-1);
                    sb.append((int) this.cfs[i]);
                } else if (this.cfs[i] == 0) {
                    sb.append("");
                    continue;
                } else if (i != 0) {
                    sb.append((int)this.cfs[i]);
                }
            }

            if (this.cfs.length >= 3) {
                if (this.cfs[i] == 0 && i != 0) {
                    sb.append("");
                    if (this.cfs.length - 1 != i && this.cfs[i+1] != 0 && this.cfs[i+1] > 0) {
                        sb.append(" + ");
                    } if (this.cfs.length - 1 != i && this.cfs[i+1] != 0 && this.cfs[i+1] <= 0) {
                        this.cfs[i+1] = this.cfs[i+1] * (-1);
                        sb.append(" - ");
                    }
                } else if (this.cfs[i] != 0) {
                    if (this.cfs.length - 1 != i && i == this.cfs.length-1 && this.cfs[i] > 0) {
                        sb.append((int)this.cfs[i]);
                    } else if (this.cfs.length-1 == i && this.cfs[i] > 0) {
                        sb.append((int) this.cfs[i]);
                    } else if (i == this.cfs.length-2 && this.cfs[i] == 1) { // Si es 1 que escriba solo x
                        sb.append("x");
                    } else if (i == this.cfs.length-2 && this.cfs[i] != 1 && this.cfs[i] >= 0) { // Si no es 1 o 0 que escriba lo que toca
                        sb.append((int) this.cfs[i] + "x");
                    } else if (i <= this.cfs.length-1 && this.cfs[i] < 0) {
                        if (i != 0) {                                          // Si es menor a 0 que lo devuelva positivo
                            this.cfs[i] = this.cfs[i] * (-1);
                        }
                        if (this.cfs.length-1 == i) {
                            sb.append((int)this.cfs[i]);
                        } else if (this.cfs.length-2 == i){
                            sb.append((int) this.cfs[i] + "x");
                        } else if (this.cfs[i] == -1) {
                            sb.append("-x^" + cont);
                        } else {
                            sb.append((int) this.cfs[i] + "x^" + cont);
                        }
                    } else if (this.cfs[i] == 1) {
                        sb.append("x^" + cont);
                    } else if (this.cfs[i] != 0 && this.cfs[i] != 1 && i != this.cfs.length-1) { // Si no que lo ponga en positivo
                            sb.append((int) this.cfs[i] + "x^" + cont);
                            if (i == this.cfs.length-2) {
                                continue;
                            }
                    } if (cont == 0) {
                        continue;
                    } if (this.cfs.length - 1 != i && this.cfs[i+1] > 0) {
                        sb.append(" + ");
                    } if (this.cfs.length - 1 != i && this.cfs[i+1] < 0) {
                        sb.append(" - ");
                    }
                } else if (this.cfs[i] == 0 && i != 0) {
                    sb.append("");
                } else if (this.cfs[i] == 0 && i == 0 && this.cfs[i+1] == 0) {
                    sb.append((int)this.cfs[i]);
                    continue;
                }
            }
            cont--;
        }
        System.out.println(Arrays.toString(this.cfs));
        return sb.toString();
    }
}