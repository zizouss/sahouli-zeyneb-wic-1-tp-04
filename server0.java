
import java.lang.Integer.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;


public class Serveur extends UnicastRemoteObject implements Service
{


int INFINI = java.lang.Integer.MAX_VALUE;



static int cpt;
calcule_produit pid_prod ;
calcule_somme pid_som ;

int i;
int [][]prod;
int [][]som;



public Serveur () throws RemoteException
{
super();
}



public static void main ( String args[] ) throws Exception
{
Serveur svr = new Serveur();

Registry registry = LocateRegistry.createRegistry(2006);
Naming.bind ("//127.0.0.1:2006/Service", svr);
System.out.println (" Attente d'appel ...");
cpt = 0;
}




class calcule_produit extends Thread
{
int m1[][], m2[][];
int i, j, k;

public calcule_produit(int [][] mat1, int [][] mat2)
{
m1 = new int [mat1.length][mat1[0].length];
m2 = new int [mat2.length][mat2[0].length];
m1 = mat1;
m2 = mat2;
}

public void run()
{
try
   {
   Thread.sleep( 40000 );
   }
   catch(Exception e)
        {
        e.printStackTrace();
        }

if (m1[0].length != m2.length)
   {
    prod[0][0] = INFINI;
    }
    else
        {
        prod = new int [m1.length][m2[0].length];
        for(i = 0; i< m1.length; i++)
           for(int k=0; k < m1[0].length; k++)
             {
          prod[i][k] = 0;
        for(int j=0; j < m1[0].length; j++)
            prod[i][k] += m1[i][j] * m2[j][k];
          }
      }
 } // run

} 




class calcule_somme extends Thread
{
int m1[][], m2[][];
int i, j, k;

public calcule_somme(int [][] mat1, int [][] mat2)
{
m1 = new int [mat1.length][mat1[0].length];
m2 = new int [mat2.length][mat2[0].length];
m1 = mat1;
m2 = mat2;
}

public void run() 
{

try
   {
   //Thread.sleep( (int) Math.random()*50000 );
   Thread.sleep( 10000 );
   }
   catch(Exception e)
        {
        e.printStackTrace();
        }

    if(m1.length != m2.length || m1[0].length != m2[0].length)
       {
       som[0][0] = INFINI;
       }
       else
            {
            som  = new int [m1.length][m1[0].length];
            for(int i = 0; i< m1.length; i++)
               for(int j=0; j < m1[0].length; j++)
                   som[i][j] = m1[i][j] + m2[i][j];
            }
}  // methode run

}// class calcule_somme extends thread 




public int [][]  produit(int [][] mat1, int [][] mat2) throws RemoteException
{
String client;
prod = new int[mat1.length][mat2[0].length];


synchronized(this)
{
if(cpt == 3)
   {
   try
     {
     System.out.println("L'appel du client "+ getClientHost() + "a ete refuse.");
     }
     catch(Exception e)
       {
        e.printStackTrace();
       }
   System.out.println(" pas de place ???");
   prod[0][0] = -INFINI;
   return prod;
   }
cpt += 1;

} // synchcronized


try
{
System.out.println("Debut de traitement associe au client "+ getClientHost());
}
    catch(Exception e)
       {
        e.printStackTrace();
       }

pid_prod = new calcule_produit(mat1, mat2);
        try
           {
           pid_prod.start();
           }
           catch(Exception e)
               {
                cpt      -=  1; 
                e.printStackTrace();
               }

try
   {
   pid_prod.join();
   }
   catch(Exception e)
       {
        cpt      -=  1; 
        e.printStackTrace();
       }
return prod;
}



public int [][]  somme(int [][] mat1, int [][] mat2) throws RemoteException
{
String client;

try
{
client = getClientHost();
}
 catch(Exception e)
 {
  System.out.println("Impossible d'avoir le nom de la machine du client ");
 }

som = new int[mat1.length][mat1[0].length];

pid_som = new calcule_somme(mat1, mat2);

try
  {
  pid_som.start();
  }
  catch(Exception e)
       {
       cpt      -=  1; 
       e.printStackTrace();
       }
try
   {
   pid_som.join();
   }
   catch(Exception e)
       {
        cpt      -=  1; 
        e.printStackTrace();
       }

synchronized (this)
 {
 cpt      -=  1;
try
 {
 System.out.println("Fin de traitement du client "+ getClientHost());
 }
    catch(Exception e)
       {
        e.printStackTrace();
       }

 return som;
 }
} 


}}
service:
import java.math.BigInteger;
import java.rmi.*;


public interface Service extends java.rmi.Remote
{

public int[][] produit( int [][] m1, int [][] m2)throws RemoteException;
public int[][] somme( int [][] m1, int [][] m2)throws RemoteException;

}

