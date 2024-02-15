package br.edu.imepac.professores.models.services;

public class SimpleMath {

    public Double sum(Double val1, Double val2){
        return val1 + val2;
    }

    public Double raizQuad(double val1){
        return Math.sqrt(val1);
    }

    public Double media(double val1, double val2){
        return (val1 + val2) / 2;
    }

    public Double division(Double val1, Double val2){
        if(val2.equals(0D))
            throw new ArithmeticException("Can not divide by Zero!");
        return val1 / val2;
    }
}
