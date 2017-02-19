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
        // Cridam al array de floats
        this.cfs = cfs;
    }

    // Constructor a partir d'un string
    public Polynomial(String s) {
        // Agafam el string que ens donen per treballar amb ell
        this.s = s;
        // Cream una variable com si fos booleana ("1" i "-1") per més endavant canviar el signe del monomi
        float mult = 1;
        // Cream un array amb un split que vagi separant els monomis per espais
        String[] ar_string = s.split(" ");
        // Feim un bucle
        for (String ss : ar_string) {
            // Si troba que l'element és un "+" que multipliqui per "1" i si és negatiu per "-1"
            if (ss.equals("+")) {
                mult = 1;
                continue;
            } if (ss.equals("-")) {
                mult = -1;
                continue;
            }
            // Ara sacam l'exponent i el coeficient dels monomis amb dos funcions externes
            int exponent = treuExponent(ss);
            float coeficient = treuCoeficient(ss)*mult;
            // Si el coeficient és 0 que no ho escrigui
            if (coeficient == 0) {
                continue;
            }
            // Afegim el coeficient i l'exponent al array
            setCoeficient(coeficient,exponent);
        }
        // Giram l'array per a que ens quedi correctament
        girar(this.cfs);
    }

    private void girar(float[] p) {
        for (int i = 0; i < this.cfs.length / 2; i++) {
            // Va ficant els valors per darrera i per endavant
            float endavant = p[i];
            p[i] = p[p.length-i-1];
            p[p.length-i-1] = endavant;
        }
    }

    private void setCoeficient(float coeficient, int exponent) {
        // Si el exponent és major a la longitud de array, crearem un array més gran
        if (exponent >= this.cfs.length) {
            // Cream un array que fiqui els coeficients en les posicions corresponents segons el seu exponent
            float[] array = new float[exponent+1];
            array[exponent] = coeficient;
            // Si el valor del array actual és menor o igual al valor del array this, ficam el this dins el actual
            for (int i = 0; i < array.length; i++) {
                if (i <= this.cfs.length-1) {
                    array[i] = this.cfs[i];
                }
            }
            this.cfs = array;
        } else {
            // Sumam el coeficient
            this.cfs[exponent] += coeficient;
        }
    }

    private float treuCoeficient(String cocient) {
        // Cream la variabe coeficient i cream un comptador
        float coeficient = 0;
        int cont = 0;
        // Feim un bucle que miri les posicions que hi ha entre l'inici i la x
        for (int i = 0; i < cocient.length(); i++) {
            if (cocient.charAt(i) != 'x') {
                cont++;
            } if (cocient.charAt(i) == 'x') {
                break;
            }

        }
        // Si conté una "x"
        if (cocient.contains("x")) {
            // Feim un bucle que miri si és positiu o negatiu
            for (int i = 0; i < cocient.length(); i++) {
                if (cocient.charAt(i) == 'x' && i == 0) {
                    // Si la "x" és el primer caràcter tornarà un 1 de coeficient
                    coeficient = 1;
                } if (cocient.charAt(i) == '-' && cocient.charAt(i+1) == 'x') {
                    // Si el primer caràcter és un "-" i el següent és "x" el coeficient serà "-1"
                    coeficient = -1;
                    return coeficient;
                } else if (cocient.charAt(i) != 'x') {
                    // Aqui tornam el coficient fins que hi hagi una x
                    coeficient = Float.parseFloat(cocient.substring(0,cont));
                }
                return coeficient;
            }
        } if (!cocient.contains("x")) {
            // Si no hi conté una "x" retornam el número complet
            coeficient = Float.parseFloat(cocient.substring(0));
        }
        return coeficient;
    }

    private int treuExponent(String exponent) {
        // Cream una variable
        int expo = 0;
        // Si el monomi no conté "^" retornarà la variable anterior amb valor 1
        if (exponent.contains("x") && !exponent.contains("^")) {
            expo = 1;
            return expo;
        } if (exponent.contains("x") && exponent.contains("^")) {
            // Si conté "^" tornarà el valor que estigui després del ^
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
        // Cream tres arrays, dos per emmagatzemar els valors que ens donen i el tercer per emmagatzemar el resultat
        float[] ar;
        float[] ar2;
        float[] resultat;
        // En aquest condicional li donam la mesura més gran possible als arrays
        if (p.cfs.length > this.cfs.length) {
            ar = new float[p.cfs.length];
            ar2 = new float[p.cfs.length];
            resultat = new float[p.cfs.length];
        } else {
            ar = new float[this.cfs.length];
            ar2 = new float[this.cfs.length];
            resultat = new float[this.cfs.length];
        }
        // Ficam els valos dels arrays this i p en els seus nous arrays en la posició corresponent.
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
        // Per ultim feim una suma dels arrays i el ficam en el array resultat
        for (int i = 0; i < ar.length; i++) {
            resultat[i] = ar2[i] + ar[i];
        }
        Polynomial result = new Polynomial(resultat);
        return result;
    }


    // Multiplica el polinomi amb un altre. No modifica el polinomi actual (this). Genera un de nou
    public Polynomial mult(Polynomial p2) {
        // Cream un array amb els espais suficients
        float[] resultat = new float[this.cfs.length+p2.cfs.length-1];
        // Feim un bucle
        for (int i = 0; i < this.cfs.length; i++) {
            // Si el valor és 0 que no fagi res
            if (this.cfs[i] == 0) {
                continue;
            } for (int j = 0; j < p2.cfs.length; j++) {
                // Ara multiplicam els coeficients i els ficam el la posició sumada dels exponents
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
        // Comparam el toString amb el polinomi que ens donen
        Polynomial p = (Polynomial) o;
        return this.toString().equals(p.toString());
    }

    // Torna la representació en forma de String del polinomi. Override d'un mètode de la classe Object
    @Override
    public String toString() {
        // He fet un comptador per no passar-me del array
        int cont = cfs.length-1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.cfs.length; i++) {
            // Si el array només té 1 element que el fiqui
            if (this.cfs.length == 1) {
                sb.append((int)this.cfs[i]);
            }
            // Si el array és de 2 posicions
            if (this.cfs.length == 2) {
                // Si el primer valor és "1" o "-1"
                if (i == 0 && this.cfs[i] == 1 || this.cfs[i] == -1) {
                    if (i == 0 && this.cfs[i+1] == 0 && this.cfs[i] != -1) {
                        // Si l'últim valor és 0 que només fiqui la x
                        sb.append("x");
                        continue;
                    } if (i == 0 && this.cfs[i] == -1 && this.cfs[i+1] > 0) {
                        // Si és negatiu i el següent positiu
                        sb.append("-x + ");
                    } else if (i == 0 && this.cfs[i] == -1 && this.cfs[i+1] < 0) {
                        // Si és negatiu i el següent negatiu
                        sb.append("-x - ");
                    } else {
                        // Si és positiu i el següent també és positiu
                        sb.append("x + ");
                    }
                } else if (i == 0 && this.cfs[i] != 1) {
                    // Si el primer valor no és 1
                    if (i == 0 && this.cfs[i+1] == 0 && this.cfs[i] != 0) {
                        // Si el primer valor no és 0 però el següent sí que fiqui el valor actual
                        sb.append((int)this.cfs[i] + "x");
                        continue;
                    } else if (this.cfs[i] == 0) {
                        // Si és 0 que fiqui 0
                        sb.append("0");
                    } else {
                        // Si no que fiqui el valor corresponent
                        sb.append((int) this.cfs[i] + "x + ");
                    }
                } else if (i == 1 && this.cfs[i] < 0) {
                    // Si és negatiu que el multipliqui per "-1"
                    this.cfs[i] = this.cfs[i] * (-1);
                    sb.append((int) this.cfs[i]);
                } else if (this.cfs[i] == 0) {
                    // Si és 0 que no fiqui res
                    sb.append("");
                    continue;
                } else if (i != 0) {
                    // Ficam l'ultim valor del array
                    sb.append((int)this.cfs[i]);
                }
            }
            // Si el array té una longitud de 3 o més.
            if (this.cfs.length >= 3) {
                if (this.cfs[i] == 0 && i != 0) {
                    // Si no hi ha res que no fiqui res
                    sb.append("");
                    // Miram el signe, si el següent és positiu o negatiu ficam "+" o "-"
                    if (this.cfs.length - 1 != i && this.cfs[i+1] != 0 && this.cfs[i+1] > 0) {
                        sb.append(" + ");
                    } if (this.cfs.length - 1 != i && this.cfs[i+1] != 0 && this.cfs[i+1] <= 0) {
                        this.cfs[i+1] = this.cfs[i+1] * (-1);
                        sb.append(" - ");
                    }
                } else if (this.cfs[i] != 0) {
                    // Si el array no està en la primera posició
                    // Ficam el valor
                    if (this.cfs.length - 1 != i && i == this.cfs.length-1 && this.cfs[i] > 0) {
                        sb.append((int)this.cfs[i]);
                    } else if (this.cfs.length-1 == i && this.cfs[i] > 0) {
                        // Si és positiu i està en la última posició que el fiqui
                        sb.append((int) this.cfs[i]);
                    } else if (i == this.cfs.length-2 && this.cfs[i] == 1) {
                        // Si es 1 que fiqui només "x"
                        sb.append("x");
                    } else if (i == this.cfs.length-2 && this.cfs[i] != 1 && this.cfs[i] >= 0) {
                        // Si no és '0' o '1' que fiqui el valor corresponent
                        sb.append((int) this.cfs[i] + "x");
                    } else if (i <= this.cfs.length-1 && this.cfs[i] < 0) {
                        // Si és menor a 0 que torni un valor positiu
                        if (i != 0) {
                            this.cfs[i] = this.cfs[i] * (-1);
                        }
                        if (this.cfs.length-1 == i) {
                            // Fica el valor si està en la ultima posició
                            sb.append((int)this.cfs[i]);
                        } else if (this.cfs.length-2 == i){
                            // Si està en la penúltima posició li ficam una "x"
                            sb.append((int) this.cfs[i] + "x");
                        } else if (this.cfs[i] == -1) {
                            // Si és negatiu li possam "-x"
                            sb.append("-x^" + cont);
                        } else {
                            // Si no li possam "x" més l'exponent
                            sb.append((int) this.cfs[i] + "x^" + cont);
                        }
                    } else if (this.cfs[i] == 1) {
                        // Si el valor és "1" que retorni "x" més el exponent
                        sb.append("x^" + cont);
                    } else if (this.cfs[i] != 0 && this.cfs[i] != 1 && i != this.cfs.length-1) {
                        // Ficam el coeficient amb x i exponent
                            sb.append((int) this.cfs[i] + "x^" + cont);
                            if (i == this.cfs.length-2) {
                                continue;
                            }
                    } if (cont == 0) {
                        // Si és 0 que no fagi res
                        continue;
                    }
                    // Miram els signes
                    if (this.cfs.length - 1 != i && this.cfs[i+1] > 0) {
                        sb.append(" + ");
                    } if (this.cfs.length - 1 != i && this.cfs[i+1] < 0) {
                        sb.append(" - ");
                    }
                } else if (this.cfs[i] == 0 && i != 0) {
                    // Si no hi ha res que no fiqui res
                    sb.append("");
                } else if (this.cfs[i] == 0 && i == 0 && this.cfs[i+1] == 0) {
                    // Si no que fiqui el valor corresponent
                    sb.append((int)this.cfs[i]);
                    continue;
                }
            }
            cont--;
        }
        return sb.toString();
    }
}