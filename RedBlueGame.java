import java.util.Scanner;

public class RedBlueGame {
    
public int maxd;

private static int[] evaluationfunction(int redballs, int blueballs, int depth,boolean turn)
{
    if(redballs == 0 || blueballs == 0 ) {

        if(turn==true)
        {
            int g[]={((2*redballs)+(3*blueballs)),1};
            return g;
        }
        else
        {
            int g[]={-((2*redballs)+(3*blueballs)),1};
            return g;
        }
        
    }
    if(depth<=0)
    {
         if(turn==true)
        {
            int g[]={((2*redballs)+(3*blueballs)),1};
            return g;
        }
        else
        {
            int g[]={-((2*redballs)+(3*blueballs)),1};
            return g;
        }
    }
        int g[]={0,-1};
        return g;
}


public static void main(String[] args) {
    int redballs = Integer.parseInt(args[0]);
    int blueballs = Integer.parseInt(args[1]);
    //deafult computer turn is first
    boolean computerturn = true;
    if(args.length >= 3 && args[2].equals("human")) {
        computerturn = false;
    }
    //default depth limit is infinity
    int maxd=Integer.MAX_VALUE;
    if(args.length >=4)
    {
        maxd=Integer.parseInt(args[3]);
    
    }
    
    int j[] = Start(redballs, blueballs, computerturn,maxd);
    System.out.println("Game over!");
    if(j[0]>0) {
     System.out.println("Computer won! " );
    } else if(j[0]<1) {
        System.out.println("Human won! ");
    } 
    System.out.println("Final score - "+j[1]);
}

private static int[] Start(int redballs, int blueballs, boolean cturn, int maxd) {
    int k=0;
    boolean val=true;
    //boolean isComputerTurn = computerturn;
    while(redballs > 0 && blueballs > 0) {
        if(cturn) {
            val=true;
            int next[] = alphaBetaDecision(redballs, blueballs,maxd);
            System.out.println("red"+redballs+" blue"+blueballs);
            System.out.println("Computer removes a " + (next[1]== 0 ? "red" : "blue") + " marble.");
            if(next[1] == 0) {
                redballs -= 1;
               
            } else {
               blueballs -= 1;
               
            }
            
        } else {
            val=false;
            Scanner in = new Scanner(System.in);
            int pile;
            do {
                System.out.println("red"+redballs+" blue"+blueballs);
                System.out.print("Enter colour of the pile to remove a marble from: (Enter 0 for red, 1 for blue): ");
                pile = in.nextInt();
            }while(pile != 0 && pile != 1);
             
                       
            if(pile == 0) {
                redballs -= 1;
                
            } else {
                blueballs -= 1;
               
            }
        }
        cturn = !cturn;
    }
    if(redballs <= 0 || blueballs <= 0)
    {
        if(!val)
        {
           k=1;

        }
        else{
            k=-1;
        }
    }
    int score=(2*redballs+3*blueballs);
    int y[] ={k,score};
    return y;
}

private static int[] alphaBetaDecision(int redballs, int blueballs,int depth) {
    
    int v[] = maximizeFunction(redballs, blueballs, Integer.MIN_VALUE, Integer.MAX_VALUE,depth-1);
    
    return v;
}



private static int[] maximizeFunction(int redballs, int blueballs, int alpha, int beta, int depth) {
    //System.out.println("depth="+depth);
    int h[]=evaluationfunction(redballs,blueballs,depth,true);
   if(h[1]==1)
   {
    return h;
   }
   else{
    int v[] = {Integer.MIN_VALUE,100};

    for(int pile = 0; pile <= 1; pile++) {
            int Changedredvalue = redballs, Changedbluevalue = blueballs;
            
            if(pile == 0) {
                Changedredvalue -= 1;
            } else {
                Changedbluevalue -= 1;
            }
         
            int a[] = minimizeFunction(Changedredvalue,Changedbluevalue,alpha, beta,depth-1);
            
           // v[0]=Math.max(v[0],a[0]); 
            if(a[0] > v[0]) {
                v[0] = a[0];
                v[1]=pile;
                
            }
            //v[1]=pile;
            if(v[0] >= beta) {
               // System.out.println("v[0]-"+v[0]+"beta"+beta);
               //System.out.println("pruned");
                return v;
            }
            alpha = Math.max(alpha, v[0]);
    }
 
    return v;
   }
}

private static int[] minimizeFunction(int redballs, int blueballs, int alpha, int beta, int depth) {
   //System.out.println("depth="+depth);
   int h[]=evaluationfunction(redballs,blueballs,depth,false);
   if(h[1]==1)
   {
    return h;
   }
   else{

    int v[] = {Integer.MAX_VALUE,100};
    for(int pile = 0; pile <= 1; pile++) {
        
        
           int Changedredvalue = redballs, Changedbluevalue = blueballs;
            if(pile == 0) {
                Changedredvalue -= 1;
            } else {
                Changedbluevalue -= 1;
            }
            
            int newV[] = maximizeFunction(Changedredvalue, Changedbluevalue, alpha, beta,depth-1);
            if(newV[0] < v[0]) {
                v[0] = newV[0];
                v[1]=pile;
                
            }
            if(v[0] <= alpha) {
              //  System.out.println("v[0]-"+v[0]+"alpha"+alpha);
            // System.out.println("pruned");
                return v;
            }
            beta = Math.min(beta, v[0]);
        
    }
    return v;
}
}
}
