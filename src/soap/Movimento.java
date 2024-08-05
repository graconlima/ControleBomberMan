package soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Movimento{
    @WebMethod public int adicionarJogador(String nome);
    @WebMethod public void moverPeca(int direcao, int jogador );
    //@WebMethod public void addMovimentoListener(MovimentoListener ml);
    @WebMethod public void notificarOuvintesMovimento(String mensagem);
}