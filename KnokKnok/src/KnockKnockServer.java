/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.net.*;
import java.io.*;

public class KnockKnockServer {
	public static final int n = 250;
	public static final float obstacle = 0.5f; 
    public static void main(String[] args) throws IOException {
    int node = 0;
    DijkstraClass dij = new DijkstraClass(); 
    float[][] treshhold = new float[n][n];
        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
        
            String inputLine = null, outputLine;
            String obst[] = inputLine.split(";");
            for(int i = 1; i < obst.length; i++){
            	String[] koo = obst[i].split(",");
            	treshhold[Integer.parseInt(koo[0])][Integer.parseInt(koo[1])] = obstacle;
            }
            int[][] graph = new int[250][250];
            // Initiate conversation with client
            //System.out.println("ccc");
            for(int i = 0; i <= n; i++){
            	for(int j = 0; j <= n; j++){
            		if(treshhold[i][j] >= obstacle){
            			node++;
            			dij.Dijkstra(graph,node,250);
            		}
            	}
            }
            /*int[][] graph ={  0 1 2 3 4 5 6 7
            				0{0,0,0,0,0,0,0,0},
		            		1{0,0,3,0,4,0,0,0},
		            		2{0,3,0,1,0,0,6,0},
		            		3{0,0,1,0,0,2,0,5},
		            		4{0,4,0,0,0,43,0,0},
		            		5{0,0,0,2,43,0,0,9},
		            		6{0,0,6,0,0,0,0,8},
		            		7{0,0,0,5,0,9,8,0}};*/
            int ans[] = dij.Dijkstra(graph, 3, 7);
            for(int j = 0; j < ans.length; j++){
            	System.out.print(ans[j]+" ");
            }
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}