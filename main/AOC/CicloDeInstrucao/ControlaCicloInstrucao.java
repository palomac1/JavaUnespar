package AOC.CicloDeInstrucao;

import java.util.*;

public class ControlaCicloInstrucao implements CicloInstrucao {
    
        // Atributos da classe 
        private Scanner scanner;
    
        // Método construtor da classe
        public ControlaCicloInstrucao() {
            scanner = new Scanner(System.in);
        }
    
        // Método para ler as entradas do usuário
        public void entradaUsuario() {
    
          
        }
    
        public void inst000001() {
            int pos, MBR;
            MBR = pos;
        }       
        
        public void inst000010() {
            int pos, dado;
            pos = dado;
        }  

        public void inst000011() {
            int pos, MBR;
            MBR += pos;
        }  

        public void inst000100() {
            int pos, MBR;
            MBR -= pos;
        } 

        public void inst000101() {
            int pos, MBR;
            MBR *= pos;
        } 

        public void inst000110() {
            int pos, MBR;
            MBR /= pos;
        } 

        public void executaInstrucao(int codInstrucao) {
            switch (codInstrucao) {
                case 0:
                int lin, instrucao;
                if (instrucao == 000111) { 
                    executaInstrucao(lin); // Simula o JUMP para a instrução identificada por lin
                } 
                    break;
                case 1:
                    // Instrução 1
                    break;
                // Adicione mais casos conforme necessário
                default:
                    // Código para instrução desconhecida ou inválida
                    break;
            }
        }
        
        public void inst000111() {
            int lin, instrucao;
            if (instrucao == 000111) { 
                executaInstrucao(lin); // Simula o JUMP para a instrução identificada por lin
            } 
        }

        public void inst001000() {
            int lin, MBR;
            if (MBR == 0) { 
                executaInstrucao(lin); // Simula o JUMP para a instrução identificada por lin
            } 
        }

        public void inst001001() {
            int lin, MBR;
            if (MBR < 0) { 
                executaInstrucao(lin); // Simula o JUMP para a instrução identificada por lin
            } 
        }

        public void inst001010() {
           double MBR = java.lang.Math.sqrt(MBR);
        }

        public void inst001011() {
            int MBR;
            MBR -= MBR;
         }

         public void inst001111() {
            int pos, MBR;
            pos = MBR;
         }

         public void inst001() {
            break;
         }
}



