
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

class Arbol {

    private Nodo raiz;

    public Arbol() {
        raiz = null;
    }

    private boolean esHoja(Nodo pr) {
        return pr.getHI() == null && pr.getHD() == null;
    }

    public void insertar(int x) {
        Nodo q = new Nodo(x);
        if (raiz == null) {
            raiz = q;
            return;
        }
        Nodo pr = raiz;
        Nodo ant = null;
        while (pr != null) {
            ant = pr;
            if (x < pr.getElem()) {
                pr = pr.getHI();
            } else if (x > pr.getElem()) {
                pr = pr.getHD();
            } else {
                return;
            }
        }
        if (x < ant.getElem()) {
            ant.setHI(q);
        } else {
            ant.setHD(q);
        }
    }

    private void preOrden(Nodo pr, JTextArea ta) {
        if (pr != null) {
            ta.append(String.valueOf(pr.getElem() + "  "));
            preOrden(pr.getHI(), ta);
            preOrden(pr.getHD(), ta);
        }
    }

    public void preOrden(JTextArea ta) {
        preOrden(raiz, ta);
    }

    private void inOrden(Nodo pr, JTextArea ta) {
        if (pr != null) {
            inOrden(pr.getHI(), ta);
            ta.append(String.valueOf(pr.getElem() + "  "));
            inOrden(pr.getHD(), ta);
        }
    }

    public void InOrden(JTextArea ta) {
        inOrden(raiz, ta);
    }

    private void PostOrden(Nodo pr, JTextArea ta) {
        if (pr != null) {
            inOrden(pr.getHI(), ta);
            PostOrden(pr.getHD(), ta);
            ta.append(String.valueOf(pr.getElem() + "  "));
        }
    }

    public void PostOrden(JTextArea ta) {
        PostOrden(raiz, ta);
    }

    public boolean Verifi(int x) {
        if (raiz == null) {
            return false;
        }
        Nodo pr = raiz;
        while (pr != null) {
            if (x <= pr.getElem() && x != pr.getElem()) {
                pr = pr.getHI();
            } else if (x >= pr.getElem() && x != pr.getElem()) {
                pr = pr.getHD();
            } else {
                return true;
            }
        }
        return false;
    }

    private int sumaElemento(Nodo p) {
        if (p == null) {
            return 0;
        } else {
            if (esHoja(p)) {
                return p.getElem();
            } else {
                int aI = sumaElemento(p.getHI());
                int aD = sumaElemento(p.getHD());
                return aI + aD + p.getElem();
            }
        }
    }

    public int sumaElemento() {
        return sumaElemento(raiz);
    }
    private int sumaElementoPar(Nodo p){
    if(p == null){
      return 0;
      }
      else{
    if (esHoja(p)&&p.getElem()%2 == 0){
      return p.getElem();
      }
     else{
     int ai = sumaElementoPar(p.getHI());
     int ad= sumaElementoPar(p.getHD());
     return (p.getElem()%2==0)?ai+ad+p.getElem():ai+ad;
      }
     }
    }
     public int sumaElementoPar(){
        return sumaElementoPar(raiz);
     }
     private int sumaElementoImpar(Nodo p){
      if(p == null){
          return 0;
    }
    else{
      if (esHoja(p)&&p.getElem()%2 != 0){
           return p.getElem();
          
                  }
      else{
        int ai = sumaElementoImpar(p.getHI());
        int ad= sumaElementoImpar(p.getHD());
       return (p.getElem()%2!=0)?ai+ad+p.getElem():ai+ad;} 
     }
    }
           
    public int sumaElementoImpar(){
      return sumaElementoImpar(raiz);
    }

    private int cantidad(Nodo p) {
        if (p == null) {
            return 0;
        } else {
            if (esHoja(p)) {
                return 1;
            } else {
                int ai = cantidad(p.getHI());
                int ad = cantidad(p.getHD());
                return ai + ad + 1;
            }
        }
    }

    public int cantidad() {
        return cantidad(raiz);
    }

    private int incompleto(Nodo p)//mask
    {
        if (p == null) {
            return 0;
        } else if (esHoja(p)) {
            return 0;
        } else {
            int ai = incompleto(p.getHI());
            int ad = incompleto(p.getHD());
            if (p.getHI() != null && p.getHD() == null || p.getHI() == null && p.getHD() != null) {
                return ai + ad + 1;
            } else {
                return ai + ad;
            }
        }
    }

    public int incompleto() {
        return incompleto(raiz);
    }

    private int cantidadHoja(Nodo p) {
        if (p == null) {
            return 0;
        } else if (esHoja(p)) {
            return 1;
        } else {
            int ai = cantidadHoja(p.getHI());
            int ad = cantidadHoja(p.getHD());
            return ai + ad;
        }
    }

    public int cantidadHoja() {
        return cantidadHoja(raiz);
    }

    private boolean hermano(int x, int y, Nodo p) {
        if (p == null || esHoja(p)) {
            return false;
        } else {
            boolean ai = hermano(x, y, p.getHI());
            boolean ad = hermano(x, y, p.getHD());
            if (ai || ad) {
                return true;
            } else {
                if (p.getHI().getElem() == x && p.getHD().getElem() == y || p.getHI().getElem() == y && p.getHD().getElem() == x) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public boolean hermano(int x, int y) {
        return hermano(x, y, raiz);
    }
    // private boolean esNodoIncompleto(Nodo p){
    // if((p.getHI()==null && p.getHD()!=null)
    //       ||(p.getHI()!=null && p.getHD()==null)){
    // return true;
    //}else{return false;}

    //}
    private int nivel(Nodo p, int nivel) {
        if (p == null) {
            return 0;
        } 
            if (p.getElem() == nivel) {
                return 1;
            }
            int ai = nivel(p.getHI(), nivel);
            int ad = nivel(p.getHD(), nivel);
            if (ai!= ad) {
                return ai + ad + 1;
            }
            return ai + ad;
            
    }

    public int nivel(int nivel) {
        return nivel(raiz, nivel);
    }
    
    
    private int getNivelEncuentre(Nodo pr,int elem){
        if (pr == null) {
            return 0;
        }
        if (elem == pr.getElem()){
            return 1;
        }
        int ai = getNivelEncuentre(pr.getHI(), elem);
        int ad = getNivelEncuentre(pr.getHD(), elem);
        if (pr.getElem() ==elem) {
            return ai +1;
        }else {
           return ad + 1;
        }        
    }
    
    public int getNivelEncuentre(int eleme) {
        return getNivelEncuentre(raiz, eleme);
    }
/*private void mostrarabuelo(Nodo p, int nivel, JTextArea ta){
    if(p ==null && esHoja(p)){
        return;   
    }
    
    if(nivel(p, p.getElem()) == nivel){
        ta.append(""+p.getElem());      
    } else{
       mostrarabuelo(p.getHI(),nivel,ta);
        mostrarabuelo(p.getHD(),nivel,ta); 
    }
    
}
public void mostrarabuelo(int nivel, JTextArea ta){
    mostrarabuelo(raiz, nivel,ta);
}*/
    public static void main(String[] args) {
        Arbol s = new Arbol();
        
    }
}//end class

