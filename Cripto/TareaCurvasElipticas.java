package Cripto;

/**
 *
 * @author Rodrigo Chavez
 */
public class TareaCurvasElipticas {

    static int p = 10007;
    static int d = 12345;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TareaCurvasElipticas curva = new TareaCurvasElipticas();
        //------------calculas todos los puntos de la curva ---------
        int cont = 1;
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < p; j++) {
                if(curva.evaluaCurva(i, j)){
                    cont++;
                }
            }
        }
        System.out.println("|G| = " + cont);
        System.out.println("\n ");
        //----------------------------------------------------------
        
        //---------calcula el orden del punto (7, 5300)--------------
        PuntoRacional punto1 = new PuntoRacional(7, 5300);
        PuntoRacional punto2 = new PuntoRacional(7, 5300);
        int orden = 1;
        if (punto2.x != -1) {
            while (true) {
                orden++;
                punto2 = curva.sumaPuntosObjeto(punto1.x, punto1.y, punto2.x, punto2.y);
                if (punto2.x == -1) {
                    break;
                }
//                System.out.println("");
            }
            System.out.println("orden " + orden);
        } else {
            System.out.println("orden " + orden);
        }
        //------------------------------------------------------------------
        //----------calcula la llave publica  Q=dP ---------------------------------
        punto1 = new PuntoRacional(7, 5300);
        punto2 = new PuntoRacional(7, 5300);
        for (int i = 1; i < d; i++) {         
            punto2 = curva.sumaPuntosObjeto(punto1.x, punto1.y, punto2.x, punto2.y);
        }
        System.out.println("la llave publica es ("+punto2.x+ " " + punto2.y+")");
        //-----------------------------------------------------------------------------
    }

    public boolean evaluaCurva(int x, int y) {
        boolean salida = false;
        if (Math.pow(y, 2) % p == (Math.pow(x, 3) + x + 1) % p) {
            //System.out.println("(" + x + "," + y + ")");
            salida = true;
            return salida;
        } else {
            return salida;
        }
    }

    public PuntoRacional sumaPuntosObjeto(int x1, int y1, int x2, int y2) {
        if (x1 == -1) {
            //System.out.println("" + x2 + " " + y2);
            PuntoRacional punto = new PuntoRacional(x2, y2);
            return punto;
        } else if (x2 == -1) {
            //System.out.println("" + x1 + " " + y1);
            PuntoRacional punto = new PuntoRacional(x1, y1);
            return punto;
        }
        if (x1 == x2 && y2 == (p - y1) % p) {
            //System.out.println("(-1,-1)");
            PuntoRacional punto = new PuntoRacional(-1, -1);
            return punto;
        }
        int alpha = 0;
        if (x1 == x2 && y1 == y2) {
            alpha = (((((int) Math.pow(x1, 2)) % p) * (3) % p) + (1) % p) * (inversoMultiplicativo(p, (y1 + y2) % p)) % p;
        } else {
            alpha = ((((y1 + (p - y2)) % p)) * (inversoMultiplicativo(p, (x1 + (p - x2)) % p))) % p;
        }
        int x3;
        x3 = (((int) Math.pow(alpha, 2)) % p + (p - ((x1 + x2) % p))) % p;
        //System.out.println("x= " + x3);
        int y3;
        y3 = (((alpha) * ((x1) + (p - x3) % p) % p) + (p - y1)) % p;
        //System.out.println("y= " + y3);
        PuntoRacional punto = new PuntoRacional(x3, y3);
        return punto;
    }

    public int inversoMultiplicativo(int p, int numero) {
        for (int i = 0; i < p; i++) {
            if ((numero * i) % p == 1) {
                //System.out.println("inverso de " + numero + " es " + i);
                return i;
            } else {

            }
        }
        return -1;
    }
}
