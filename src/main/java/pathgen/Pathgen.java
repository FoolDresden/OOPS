import java.Math.*;
import java.io.*;
class Pathgen{

 int dist[][];
 int n;
 Pathgen()
 {
    dist = new int[5][5];
    n =6;
 }
void floyd_warshall(){
    for(int k=0;k<n;k++){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                dist[i][j] = min(dist[i][k]+dist[k][j],dist[i][j]);
                }
            }
        }
    }
 
 void init_paths()
 {
    for(int i = 0; i < n;++i)
    {
        for(int  j = 0; j<n; ++j)
        {
            if(i == j)
            {
                dist[i][j] = 0;
            }
            else
            {
                dist[i][j] = 9999;
            }
        }
    }

    dist[0][1] = dist[1][0] = 5;

    dist[1][2] = dist[2][1] = 6;

    dist[2][3] = dist[3][2] = 4;

    dist[1][3] = dist[3][1] = 3;

    dist[3][4] = dist[4][3] = 2;

    dist[2][4] = dist[4][2] = 1;
 }

 void dump_paths()
 {
    floyd_warshall();
    FileOutputStream f1 = new FileOutputStream("./Pathgen.txt");

    for(int i = 0;i<n;i++)
    {
        for(int j = 0 ;j<n ;j++)
        {
            f1.write((char)(i+65)+" "+(char)(j+65)+" "+(Integer)dist[i][j].toString()+"\n");
        }
    }
    f1.close();
 }


}

class Demo
{
     public static void main(String args[])
    {
        Pathgen p1 = new Pathgen();
        p1.init_paths();
        p1.dump_paths();
    }
}