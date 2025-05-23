public class Polynomial {
    shahdLinkedList terms;

    public Polynomial() {
        terms = new shahdLinkedList();
    }

    public void addTerm(int coef, int expo) {
        if (coef == 0) return;
        Term newTerm = new Term(coef, expo);

        for (int i = 0; i < terms.size(); i++) {
            Term existing = (Term) terms.getIndex(i);
            if (existing.exponent == expo) {
                existing.coefficient += coef;
                return;
            }
            if (existing.exponent < expo) {
                terms.addIndex(i, newTerm);
                return;
            }
        }
        terms.addlast(newTerm);
    }

    public Polynomial add(Polynomial other) {
        Polynomial result = new Polynomial();
        for (int i = 0; i < this.terms.size(); i++) {
            Term t = (Term) this.terms.getIndex(i);
            result.addTerm(t.coefficient, t.exponent);
        }
        for (int i = 0; i < other.terms.size(); i++) {
            Term t = (Term) other.terms.getIndex(i);
            result.addTerm(t.coefficient, t.exponent);
        }
        return result;
    }

    public Polynomial subtract(Polynomial other) {
        Polynomial result = new Polynomial();
        for (int i = 0; i < this.terms.size(); i++) {
            Term t = (Term) this.terms.getIndex(i);
            result.addTerm(t.coefficient, t.exponent);
        }
        for (int i = 0; i < other.terms.size(); i++) {
            Term t = (Term) other.terms.getIndex(i);
            result.addTerm(-t.coefficient, t.exponent);
        }
        return result;
    }

    public Polynomial multiply(Polynomial other) {
        Polynomial result = new Polynomial();
        for (int i = 0; i < this.terms.size(); i++) {
            Term t1 = (Term) this.terms.getIndex(i);
            for (int j = 0; j < other.terms.size(); j++) {
                Term t2 = (Term) other.terms.getIndex(j);
                int newCoef = t1.coefficient * t2.coefficient;
                int newExpo = t1.exponent + t2.exponent;
                result.addTerm(newCoef, newExpo);
            }
        }
        return result;
    }

    public Polynomial[] divide(Polynomial divisor) {
        if (divisor.terms.isEmpty()) {
            throw new ArithmeticException(" Cannot divide by an empty polynomial.");
        }

        boolean isZeroDivisor = true;
        for (int i = 0; i < divisor.terms.size(); i++) {
            Term t = (Term) divisor.terms.getIndex(i);
            if (t.coefficient != 0) {
                isZeroDivisor = false;
                break;
            }
        }

        if (isZeroDivisor) {
            throw new ArithmeticException(" Cannot divide by a zero polynomial.");
        }

        Polynomial dividend = this.copy();
        Polynomial quotient = new Polynomial();

        while (!dividend.terms.isEmpty()) {
            Term leadDividend = (Term) dividend.terms.getIndex(0);
            Term leadDivisor = (Term) divisor.terms.getIndex(0);

            
            System.out.println(" Current Dividend: " + dividend);

            if (leadDividend.exponent < leadDivisor.exponent) {
                break;
            }

            int newCoef = leadDividend.coefficient / leadDivisor.coefficient;
            int newExpo = leadDividend.exponent - leadDivisor.exponent;

            quotient.addTerm(newCoef, newExpo);

            Polynomial subtractPart = new Polynomial();
            for (int i = 0; i < divisor.terms.size(); i++) {
                Term t = (Term) divisor.terms.getIndex(i);
                subtractPart.addTerm(t.coefficient * newCoef, t.exponent + newExpo);
            }

            System.out.println(" Subtracting: " + subtractPart);

            Polynomial newDividend = dividend.subtract(subtractPart);

            if (newDividend.toString().equals(dividend.toString())) {
                System.out.println(" No progress in division. Exiting loop to prevent infinite repetition.");
                break;
            }

            dividend = newDividend;
        }

        return new Polynomial[]{quotient, dividend};
    }

    public Polynomial copy() {
        Polynomial copy = new Polynomial();
        for (int i = 0; i < this.terms.size(); i++) {
            Term t = (Term) this.terms.getIndex(i);
            copy.addTerm(t.coefficient, t.exponent);
        }
        return copy;
    }

    public int evaluate(int x) {
        int result = 0;
        for (int i = 0; i < terms.size(); i++) {
            Term t = (Term) terms.getIndex(i);
            result += t.coefficient * Math.pow(x, t.exponent);
        }
        return result;
    }

    public String toInfixString() {
        return this.toString();
    }

    public String toPostfixString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            Term t = (Term) terms.getIndex(i);
            sb.append(t.toString()).append(" ");
        }
        sb.append("+ ".repeat(Math.max(0, terms.size() - 1)));
        return sb.toString().trim();
    }

    public String toPrefixString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+ ".repeat(Math.max(0, terms.size() - 1)));
        for (int i = 0; i < terms.size(); i++) {
            Term t = (Term) terms.getIndex(i);
            sb.append(t.toString()).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < terms.size(); i++) {
            Term t = (Term) terms.getIndex(i);
            if (i > 0 && t.coefficient > 0) sb.append(" + ");
            sb.append(t.toString());
        }
        return sb.toString();
    }
}
