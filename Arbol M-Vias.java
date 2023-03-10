package ArbolM_Vias;

import javax.swing.JTextArea;

/**
 *
 * @author Estudiante
 */
public class ArbolM {

    private Nodo raiz;

/* constructor */

    public ArbolM() {
        raiz = null;
    }

/* metodos auxiliares */
    private boolean esHoja(Nodo P) {
        for (int i = 1; i <= P.M; i++) {
            if (P.getHijo(i) != null) {
                return false;
            }
        }
        return true;
    }

    private int getHijoDesc(Nodo P, int x) {
        int i = 1;
        while (i < P.M) {
            if (x < P.getElem(i)) {
                return i;
            }
            if (x == P.getElem(i)) {
                return -1;
            }
            i++;
        }
        return P.M;
    }

    private void InsertaOrd(Nodo P, int x) {
        for (int i = 1; i < P.M; i++) {
            if (P.Vacio(i)) {
                P.setElem(x, i);
                return;
            } else if (x == P.getElem(i)) {
                return;
            } else if (x < P.getElem(i)) {
                Recorrer(P, i);
                P.setElem(x, i);
                return;
            }
        }
    }

    private void Recorrer(Nodo P, int i) {
        int num1 = P.getElem(i);
        int num2 = 0;
        int c = P.CantOcupados();
        while (i <= c) {
            if (P.Ocupado(i + 1)) {
                num2 = P.getElem(i + 1);
            }
            P.setElem(num1, i + 1);
            num1 = num2;
            i++;
        }
    }

    public void Insertar(int x) {
        if (raiz == null) {
            raiz = new Nodo();
            raiz.setElem(x, 1);
        } else {
            Nodo P = raiz;
            Nodo AP = null;
            int i = 0;
            while (P != null) {
                AP = P;
                if (!P.Lleno()) {
                    InsertaOrd(P, x);
                    return;
                }
                i = getHijoDesc(P, x);
                if (i == -1) {
                    return; // x esta en el arbol
                }
                P = P.getHijo(i);
            }
            Nodo Q = new Nodo();
            Q.setElem(x, 1);
            AP.setHijo(Q, i);
        }
    }

    public void InOrden(JTextArea ta) {
        InOrden(raiz, ta);
    }

    private void InOrden(Nodo P, JTextArea jta) {
        if (P == null) {
            return;
        } else if (esHoja(P)) {
            int i = 1; //muestra todos los elementos de nodo P
            while (i <= P.CantOcupados()) {
                jta.append(String.valueOf(P.getElem(i) + " "));
                i++;
            }
        } else {
            for (int i = 1; i <= P.M - 1; i++) {
                InOrden(P.getHijo(i), jta);
                jta.append(String.valueOf(P.getElem(i) + " "));
            }
            InOrden(P.getHijo(P.M), jta);
        }
    }
    
    public int cantidadNodos(){
        return cantidadNodos(raiz);
    }

    private int cantidadNodos(Nodo R) {
        if(R == null)
            return 0;
        if(esHoja(R))
            return 1;
        int c = 0;
        for(int i = 1; i <= R.M; i++)
            c = c + cantidadNodos(R.getHijo(i));
        return c + 1;
        
    }
    
    public int cantidadElementos(){
        return cantidadElementos(raiz);
    }

    private int cantidadElementos(Nodo R) {
        if(R == null)
            return 0;
        if(esHoja(R))
            return R.CantOcupados();
        int c = 0;
        for(int i = 1; i <= R.M; i++)
            c = c + cantidadElementos(R.getHijo(i));
        return c + R.CantOcupados();

    }

    public int sumarElementos(){
        return sumarElementos(raiz);
    }

    private int sumarElementos(Nodo R) {
        if(R == null)
            return 0;
        if(esHoja(R))
            return sumar(R);
        int c = 0;
        for(int i = 1; i <= R.M; i++)
            c = c + sumarElementos(R.getHijo(i));
        return c + sumar(R);
    }

    private int sumar(Nodo R) {
        int s = 0, i = 1;
        while (i <= R.CantOcupados()){
            s = s + R.getElem(i);
            i++;
        }    
        return s;    
    }
    
    public int getAltura(){
        return getAltura(raiz);
    }

    private int getAltura(Nodo R) {
        if(R == null)
            return 0;
        if(esHoja(R))
            return 1;
        int mayor = getAltura(R.getHijo(1));
        for(int i = 1; i < R.M; i++){
            int posibleMayor = getAltura(R.getHijo(i+1));
            if(posibleMayor > mayor){
                mayor = posibleMayor;
            }
        }
        return mayor + 1;
    }
    
    public int getMenor(){
        return getMenor(raiz);
    }

    private int getMenor(Nodo R) {
        if(R == null)
            return 0;
        if(esHoja(R))
            return R.menor();
        int menor = getMenor(R.getHijo(1));
        for(int i = 1; i < R.M; i++){
            int posibleMenor = getMenor(R.getHijo(i+1));
            if(posibleMenor < menor && posibleMenor != 0)
                menor = posibleMenor;
        }
        int posibleMenor = R.menor();
        if(posibleMenor < menor && posibleMenor != 0)
            return posibleMenor;
        else
            return menor;
    }

/* Examenes Pracicos */
    
/* indicar el nivel al que pertenece un dato en el arbol*/

    public int queNivel(int x){
        return queNivel(raiz, x, 1);
    }

    private int queNivel(Nodo R, int x, int nivelInicial) {
        if(R == null)
            return 0;
        if(esHoja(R)){
            if(existeDato(R, x))
                return nivelInicial;
            else
                return 0;
        }else{
            for(int i = 1; i < R.M; i++){
                if(x < R.getElem(i)){
                    return queNivel(R.getHijo(i), x, nivelInicial + 1);
                }
                if(x == R.getElem(i))
                    return nivelInicial;
            }
            if(x > R.getElem(R.M - 1)){
                return queNivel(R.getHijo(R.M), x, nivelInicial + 1);
            }
            if(existeDato(R.getHijo(R.M), x))
                return nivelInicial;
            else
                return 0;
        }
    }
        
    private boolean existeDato(Nodo R, int x) {
        for(int i = 1; i < R.M; i++){
            if(x == R.getElem(i))
                return true;
        }
        return false;
    }

/* Mostrar la suma de los datos en un nivel dado por parametro */

    public int sumarNivel(int n){

        return sumarNivel(raiz, n);
    
    }

    
    
    private int sumarNivel(Nodo R, int n) {

        if(R == null)
            
           return 0;
        
        if( n == 1)
            
           return suma(R);
        
        int c = 0;
        
        for(int i = 1; i <= R.M && n > 1; i++){

           c = c + sumarNivel(R.getHijo(i), n-1);
        
        }
        
        return c;
    
    }

    
    
    private int suma(Nodo R) {

        int c = 0;
        
        for(int i = 1; i < R.M; i++)
            
            c = c + R.getElem(i);
        
        return c;
    
    }

/* mostrar los elementos de derecha a izquierda de una nivel dado por teclado
   almacenar en un lista y despues mostrar */
 
   public void mostrarNivelDerIzq(int n, JTextArea jta){

        List<Integer> lista = new LinkedList<Integer>();
        
        lista = mostrarNivelDerIzq(raiz, lista, n);
               
        jta.append(lista.toString);
    
   }    

    private boolean VerificarSiEsLista(Nodo pr) {
        if (pr != null) {
            if ( pr.CantNodoOcupado(pr)> 1) {
                return false;
            }   
            for (int i = 1; i <= pr.M; i++) {
                boolean nd = nd = VerificarSiEsLista(pr.getHijo(i));    
                if ( !nd) {
                    return nd;
                } 
            }             
        }
        return true;
    }
    
    public void VerificarEsListaEnlazada() {
        System.out.println("" + VerificarSiEsLista(raiz));
    }

   private List<Integer> mostrarNivelDerIzq(Nodo P, List<Integer> lista, int n) {

        if(P == null){

            return lista;

        }

        if(n == 1){

            for(int i = P.CantOcupados(); i > 0; i--){

                lista.add(P.getElem(i));

            }

            return lista;

        }

        for(int i = P.M; i > 0; i--){

            lista = mostrarNivelDerIzq(P.getHijo(i), lista, n - 1);

        }

        return lista;

    }
