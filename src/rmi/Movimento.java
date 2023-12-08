package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface Movimento extends Remote{
    public int adicionarJogador(String nome) throws RemoteException;
    public void moverPeca(int direcao, int jogador ) throws RemoteException;
    public void addMovimentoListener(MovimentoListener ml) throws RemoteException;
    public void notificarOuvintesMovimento(String mensagem) throws RemoteException;
}
