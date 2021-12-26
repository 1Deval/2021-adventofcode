package advent.day18;

import advent.utils.Color;

import java.util.Objects;

public class Complex implements SnailFish {
    SnailFish left;
    SnailFish right;

    public Complex(final SnailFish left, final SnailFish right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public SnailFish add(final SnailFish snailFish) {
        return new Complex(this, snailFish);
    }

    @Override
    public SnailFish explode(final int level) {


        if (left instanceof Complex && level >= 3 && ((Complex) left).right instanceof Literal && ((Complex) left).left instanceof Literal) {
            System.out.println("to be forgotten left " + left);
            if (right instanceof Complex) {
                ((Complex) right).addLiteralToLeft((Literal) ((Complex) left).right);
            } else {
                right = right.add(((Complex) left).right);
            }
            final Complex resp = new Complex(new Literal(((Literal) (((Complex) left).left)).value), new Literal(0));
            left = new Literal(0);
            return resp;
        } else if (right instanceof Complex && level >= 3 && ((Complex) right).left instanceof Literal && ((Complex) right).right instanceof Literal) {
            System.out.println("to be forgotten right " + right);
            if (left instanceof Complex) {
                ((Complex) left).addLiteralToRight((Literal) ((Complex) right).left);
            } else {
                left = left.add(((Complex) right).left);
            }
            final Complex resp = new Complex(new Literal(0), new Literal(((Literal) (((Complex) right).right)).value));
            right = new Literal(0);
            return resp;
        } else {
            final Complex leftExplosion = (Complex) left.explode(level + 1);
            if (leftExplosion != null) {
                if (right instanceof Literal) {
                    right = right.add(leftExplosion.right);
                } else {
                    ((Complex) right).addLiteralToLeft((Literal) leftExplosion.right);
                }
                return new Complex(leftExplosion.left, new Literal(0));
            }
            final Complex rightExplosion = (Complex) right.explode(level + 1);
            if (rightExplosion != null) {
                if (left instanceof Literal) {
                    left = left.add(rightExplosion.left);
                } else {
                    ((Complex) left).addLiteralToRight((Literal) rightExplosion.left);
                }
                return new Complex(new Literal(0), rightExplosion.right);
            }
        }

        return null;
    }

    @Override
    public SnailFish split() {
        left = left.split();
        right = right.split();
        return this;
    }

    @Override
    public String coloredString(final int level) {
        final String toString = String.format("[%s,%s]", left.coloredString(level + 1), right.coloredString(level + 1));
        if (level > 3) {
            return Color.ANSI_RED.wrap(toString);
//        } else if (level == 3) {
//            return Color.ANSI_YELLOW.wrap(toString);
        } else {
            return toString;
        }
    }

    void addLiteralToLeft(final Literal value) {
        if (left instanceof Literal) {
            left = left.add(value);
        } else if (left instanceof Complex) {
            ((Complex) left).addLiteralToLeft(value);
        }
    }

    void addLiteralToRight(final Literal value) {
        if (right instanceof Literal) {
            right = right.add(value);
        } else if (right instanceof Complex) {
            ((Complex) right).addLiteralToRight(value);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]", left, right);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Complex)) {
            return false;
        }
        final Complex complex = (Complex) o;
        return Objects.equals(left, complex.left) && Objects.equals(right, complex.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
