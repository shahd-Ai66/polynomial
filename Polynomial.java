/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainapp;

/**
 *
 * @author AC
 */
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
