import javax.swing.*;
import java.util.LinkedList;

public class Grafo
{
    private Lista LVertices;
    
    public Grafo() {
        LVertices = new Lista();
    }
    
    public void crearVertice(String nomV){
        LVertices.insertarUlt(new Vertice(nomV));
    }

    public Vertice buscarVertice(String nomV) {
        Vertice vertice;
        int i=0;
        while (i<LVertices.dim())  {
            vertice =(Vertice)LVertices.getElem(i);
            if (vertice.getNombre().equals(nomV))
                 return vertice;
            i++;
        }
          return null;
    }
    
    public void insertarArco(String X,String Y, float co)
    {
        Vertice vo = buscarVertice(X);
        Vertice vd = buscarVertice(Y);
        vo.insertarArco(new Arco(vd, co));        
    }
            
    public void imprimir(JTextArea jta){
        int i = 0; Vertice v; Arco a;
        while (i < LVertices.dim()) {
            v = (Vertice)LVertices.getElem(i);
            int j=0; 
            while (j<v.LArcos.dim())
            {
                jta.append(v.getNombre());
                jta.append("-->");
                a = (Arco)v.LArcos.getElem(j); //Muestra el arco donde apunto
                jta.append(a.getNombreVertD() + "  " + a.getCosto());
                jta.append("\n");
                j++;
            }
                i++;                    
        }
    }     
    public void mostrarCaminosRecorridoBFS( String verticeOrigen, String verticeDestino ){
        Vertice v = buscarVertice( verticeOrigen );
        Vertice w = buscarVertice( verticeDestino );
        if( v == null || w == null) return;
        
        LinkedList<LinkedList<String>> cola = new LinkedList();
        LinkedList<String> camino = new LinkedList();
        
        camino.add( verticeOrigen );
        cola.add(camino);
        
        do {
            camino = cola.pop();
            v = buscarVertice( camino.getLast() );
            if( v.getNombre().equals(verticeDestino )){
                System.out.println( camino );
            }
            
            for (int i = 0; i < v.LArcos.dim(); i++) {
                Arco a = (Arco)v.LArcos.getElem(i);
                w = buscarVertice( a.getNombreVertD() );
                if( !camino.contains(w.getNombre())){
                    LinkedList<String> nuevoCamino = new LinkedList( camino );
                    nuevoCamino.add(w.getNombre());
                    cola.add(nuevoCamino);
                }
            }
            
        } while (!cola.isEmpty());
    }
    //METODOS DE ENSEÑANZA    Sumar peso
    public float peso()
    {
        int i = 0;
        Vertice v; float s = 0;
        while (i < LVertices.dim())
        {
                v = (Vertice)LVertices.getElem(i);
                int j=0; Arco a;
                while(j < v.LArcos.dim())
                {
                  a=(Arco)v.LArcos.getElem(j);
                  s = s + a.getCosto();
                  j++;
                }
                i++;
            }
            return s;
        }

   
    ///
    
    
    public void desmarcarTodos() 
    {
       for(int i=0;i<this.LVertices.dim();i++){
          Vertice v=(Vertice)this.LVertices.getElem(i);
          v.marcado=false;
       }
    }
     
         
    public void ordenarVerticesAlf() {
      Vertice aux; Vertice v1; Vertice v2;
      for(int i=0;i<LVertices.dim();i++){
        for(int j=0;j<LVertices.dim()-1;j++){
            v1=(Vertice)LVertices.getElem(j);
            v2=(Vertice)LVertices.getElem(j+1);
            if(v1.getNombre().compareTo(v2.getNombre())>0){             
                aux=(Vertice)LVertices.getElem(j);                
                LVertices.setElem(v2, j);
                LVertices.setElem(aux, j+1);                                                   
            }      
        }  
      }
      for(int i=0;i<LVertices.dim();i++){
        Vertice v=(Vertice)LVertices.getElem(i);
        v.ordenarArcosAlf();
      }          
    }   
    
    public void DFS(String A, JTextArea jta){
        jta.append("DFS: ");
        desmarcarTodos();
        ordenarVerticesAlf();
        Vertice v = buscarVertice(A);
        dfs(v,  jta);
        jta.append("\n");
    }

    private void dfs(Vertice v, JTextArea jta){
        jta.append(v.getNombre() + " ");
        v.marcado=true;
        Arco a;
        for (int i = 0; i < v.LArcos.dim(); i++) {
            a = (Arco) v.LArcos.getElem(i);
            Vertice w = buscarVertice(a.getNombreVertD());
            if(!w.marcado)
            dfs(w, jta);
        }
    }
    
    

    public void BFS(String s,JTextArea jta)
    {  
       desmarcarTodos();
       ordenarVerticesAlf();
       Arco a;
       Vertice v = buscarVertice(s), w;
       LinkedList <Vertice> C;
       C=new LinkedList<Vertice>();
       C.add(v);    
       v.marcado=true;
       jta.append("BFS: ");
       do{
           v = C.pop();
           jta.append(v.getNombre() + " ");
           for (int i = 0; i < v.LArcos.dim(); i++) { 
               a = (Arco) v.LArcos.getElem(i);
               w = buscarVertice(a.getNombreVertD());
               if (!w.marcado) {
                   C.add(w);
                   w.marcado=true;
               }
           }
       }while (!C.isEmpty());   
       jta.append("\n");
    }   
    
  
    
  
    public boolean esArbolBinario(String x) { // Por dfs
        Vertice vo = buscarVertice(x);
        vo.marcado = true;   
        if (vo.LArcos.dim() <= 2) {
            Arco a; int i=0;
            while (i < vo.LArcos.dim()) {
                a =  (Arco) vo.LArcos.getElem(i);
                Vertice w = buscarVertice(a.getNombreVertD());
                if (!w.marcado) {
                    if (!esArbolBinario(a.getNombreVertD())) {
                        return false;
                    }
                } else {
                    return false;
                }
                i++;
            }
            return true;
        }
        return false;
    }
    
    private void MostrarTodosLosCaminos(Vertice v,LinkedList<String> l,JTextArea jta){
        Arco a;
        for (int i=0;i<v.LArcos.dim();i++){
            a=(Arco)v.LArcos.getElem(i);
            l.add(a.getNombreVertD());
            jta.append(l.toString()+"\n");
            Vertice p=buscarVertice(a.getNombreVertD());
            MostrarTodosLosCaminos(p,l,jta);
            l.removeLast();
         }
    }
    
    public void MostrarTodosLosCaminos(JTextArea jta){
        Vertice v;
        LinkedList<String> l=new LinkedList<String>();
        for (int i=0;i<LVertices.dim();i++){
            v=(Vertice)LVertices.getElem(i);
            l.add(v.getNombre());
            MostrarTodosLosCaminos(v, l,jta);
            l.clear();
        }
    }
    
    private void MostrarCaminos(Vertice x,Vertice y,LinkedList<String> l,JTextArea jta){
        /* if (existeCamino(x.getNombre(), y.getNombre())){
            Arco a;
            for (int i=0;i<x.LArcos.dim();i++){
                a=(Arco)x.LArcos.getElem(i);
                l.add(a.getNombreVertD());
                if (l.getLast().equals(y.getNombre())){
                   jta.append(l.toString()+"\n");
                }
                Vertice v=buscarVertice(a.getNombreVertD());
                if (!l.getLast().equals(a.getNombreVertD())) {
                    MostrarCaminos(x, y, l, jta);
                    l.removeLast();
                }
            }
        }*/
    }
    
    public void MostrarCaminos(String x,String y,JTextArea jta){
        Vertice v1=buscarVertice(x);
        Vertice v2=buscarVertice(y);
        LinkedList<String> l=new LinkedList<String>();
        l.add(v1.getNombre());
        MostrarCaminos(v1, v2, l,jta);
        l.clear();
    }
    
    public void CantArcosSalientes() {
        int i = 0;
        Vertice v;
        while (i < LVertices.dim()) {
            v = (Vertice) LVertices.getElem(i);
            JOptionPane.showMessageDialog(null, v.getNombre() + " " + v.LArcos.dim());
            i++;
        }
    }

    public void CantArcosentrantes() {
        int i = 0;
        Vertice v, v1;
        Arco a;
        while (i < LVertices.dim()) {
            v = (Vertice) LVertices.getElem(i);
            int j = 0;
            int c = 0;
            while (j < LVertices.dim()) {
                v1 = (Vertice) LVertices.getElem(j);
                int k = 0;
                while (k < v1.LArcos.dim()) {
                    a = (Arco) v1.LArcos.getElem(k);
                    if (v.getNombre().equals(a.getNombreVertD())) {
                        c++;
                    }
                    k++;
                }
                j++;
            }
            i++;
            JOptionPane.showMessageDialog(null, v.getNombre() + " " + Integer.toString(c));
        }
    }
   public void EliminarArcosSalientes(String x){
       Vertice v;
       v=buscarVertice(x);
       int i=0; int n=v.LArcos.dim();
       while(i<n){
           v.LArcos.eliminarUlt();
           i++;
       }    
   }
   public void EliminarArcosEntrantes(String x){
       Vertice v;Arco a;
       int i=0,j;
       while(i<LVertices.dim()){
           v=(Vertice)LVertices.getElem(i);
           j=0; int n=v.LArcos.dim();
           while(j<n){
               a=(Arco)v.LArcos.getElem(j);
               if(a.getNombreVertD().equals(x)){
                   v.LArcos.eliminar(j);
               }
               j++;
           }
           i++;
       }
   } 
   public void EliminarVertice(String x){
       Vertice v;
       v=buscarVertice(x);
       if(v==null){
           JOptionPane.showMessageDialog(null, "No existe Vertice");
       }else{
           EliminarArcosEntrantes(x);
           EliminarArcosSalientes(x);
           int i=0;
           while(i<LVertices.dim()){
               v=(Vertice)LVertices.getElem(i);
               if(v.getNombre().equals(x)){
                   LVertices.eliminar(i);
                   return;
               }
           }
       }
   }
   public void MostrarCaminosCostoBajo(String x, String y, JTextArea jta) {
        Vertice v1 = buscarVertice(x);
        Vertice v2 = buscarVertice(y);
        LinkedList<String> l = new LinkedList<String>();
        LinkedList<Float> costo = new LinkedList<Float>();
        l.add(v1.getNombre());
        MostrarCaminosCostoBajo(v1, v2, l, jta, 0, costo);
        l.clear();
        float bajo = costo.getFirst();
        for (int i = 0; i < costo.size(); i++) {
            if (costo.get(i) < bajo) {
                bajo = costo.get(i);
            }
        }
        jta.append("El costo mas bajo es: " + bajo + "\n");
    }
    public boolean cantantidadCaminos(String x, String y, int cantidad) {
        Vertice vo = buscarVertice(x);
        Vertice vd = buscarVertice(y);
        LinkedList<String> l = new LinkedList<String>();
        l.add(vo.getNombre());
        int cantCaminos = 0;
        cantidadCaminos(vo, vd, l, cantCaminos);
        l.clear();
        if (cantidad == cantCaminos) {
            return true;
        } else {
            return false;
        }
    }

    private void cantidadCaminos(Vertice x, Vertice y, LinkedList<String> l, int cantCaminos) {
        if (existeCamino(x.getNombre(), y.getNombre())) {
            Arco a;
            for (int i = 0; i < x.LArcos.dim(); i++) {
                a = (Arco) x.LArcos.getElem(i);
                if (!l.contains(a.getNombreVertD())) {
                    l.add(a.getNombreVertD());
                    if (l.getLast().equals(y.getNombre())) {
                        cantCaminos++;
                    }
                    Vertice v = buscarVertice(a.getNombreVertD());
                    cantidadCaminos(v, y, l, cantCaminos);
                    l.removeLast();
                }
            }

        }
    }

    private void MostrarCaminosCostoBajo(Vertice x, Vertice y, LinkedList<String> l, JTextArea jta, float suma, LinkedList<Float> costo) {
        if (existeCamino(x.getNombre(), y.getNombre())) {
            Arco a;
            for (int i = 0; i < x.LArcos.dim(); i++) {
                a = (Arco) x.LArcos.getElem(i);
                if (!l.contains(a.getNombreVertD())) {
                    suma = suma + a.getCosto();
                    l.add(a.getNombreVertD());
                    if (l.getLast().equals(y.getNombre())) {
                        costo.add(suma);
                        jta.append(l.toString() + " y su costo= " + suma + "\n");
                    }
                    Vertice v = buscarVertice(a.getNombreVertD());
                    MostrarCaminosCostoBajo(v, y, l, jta, suma, costo);
                    l.removeLast();
                    suma = suma - a.getCosto();
                }
            }
        }
    }
    private boolean esTernario(Vertice vo) {
        vo.marcado = true;
        if (vo.LArcos.dim() <= 3) {
            boolean b = true;
            for (int i = 0; i < vo.LArcos.dim(); i++) {
                Arco a = (Arco) vo.LArcos.getElem(i);
                Vertice vproximo = buscarVertice(a.getNombreVertD());
                if (vproximo.marcado) {
                    return false;
                }
                b = esTernario(vproximo);
                if (!b) {
                    return false;
                }
            }
            return b;
        } else {
            return false;
        }
    }

    public boolean esTernario(String origen) {
        desmarcarTodos();
        Vertice vo = buscarVertice(origen);
        return esTernario(vo);
    }
   public void EliminarArco(String x, String y){
       Vertice v,v1;
       v=buscarVertice(x);
       v1=buscarVertice(y);
       if(v!=null && v1!=null){
           int i=0;Arco a;
           while(i<v.LArcos.dim()){
               a=(Arco)v.LArcos.getElem(i);
               if(a.getNombreVertD().equals(y)){
                   v.LArcos.eliminar(i);
                   return;
               }
               i++;
           }
       }
       public boolean iguales(Grafo G2) {
        if (this.LVertices.dim() != G2.LVertices.dim()) {
            return false;
        }
        int i = 0;
        int j = 0;
        while (i < this.LVertices.dim()) {
            Vertice v1 = (Vertice) this.LVertices.getElem(i);
            Vertice v2 = (Vertice) G2.LVertices.getElem(i);
            if (!v1.getNombre().equals(v2.getNombre()) || (v1.LArcos.dim() != v2.LArcos.dim())) {
                return false;
            } else {
                while (j < v1.LArcos.dim()) {
                    Arco a1 = (Arco) v1.LArcos.getElem(j);
                    Arco a2 = (Arco) v2.LArcos.getElem(j);
                    if (!a1.getNombreVertD().equals(a2.getNombreVertD()) || a1.getCosto() != a2.getCosto()) {
                        return false;
                    }
                    j++;
                }
            }
            i++;
        }
        return true;
    }
   }
