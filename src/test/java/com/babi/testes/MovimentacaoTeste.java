package com.babi.testes;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.babi.core.BaseTeste;
import com.babi.pages.MenuPage;
import com.babi.pages.MovimentacaoPage;
import com.babi.utils.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovimentacaoTeste extends BaseTeste{

       private MenuPage menuPage = new MenuPage();
       private MovimentacaoPage mPage = new MovimentacaoPage();
       private DataUtils dataUtil = new DataUtils();
	
	@Test
	public void teste1_inserirMovimentacao() {
		menuPage.criarMovimentacao();
		
		mPage.selecionarTipoMovimentacao("Receita");
		mPage.setDataMovimentacao(dataUtil.obterdataFormatada(new Date()));
		mPage.setDataPagamento(dataUtil.obterdataFormatada(new Date()));
		mPage.setDescricao("pagamento cliente");
		mPage.setInteressado("fulano");
		mPage.setValor("200");
		mPage.selecionaConta("Conta testada");
		mPage.setStatusPago();
		mPage.salvar();
		
		Assert.assertEquals("Movimentação adicionada com sucesso!", mPage.obterMensagemSucesso());   
		
		
	}
	
	@Test
	public void test2_validarCamposObrigatorios() {
		menuPage.criarMovimentacao();
		
		mPage.salvar();
		List<String> erros = mPage.obterErros();
		//Assert.assertEquals("Data da Movimentação é obrigatório", erros.get(0));
		//Assert.assertTrue(erros.contains("Data da Movimentação é obrigatório"));
		Assert.assertTrue(erros.containsAll(Arrays.asList("Data da Movimentação é obrigatório", 
				"Data do pagamento é obrigatório", "Descrição é obrigatório", "Interessado é obrigatório", 
				"Valor é obrigatório", "Valor deve ser um número")));
		Assert.assertEquals(6, erros.size());
	
		
	}
	
	@Test
	public void test3_inserirMovimentacaoFutura() {
        menuPage.criarMovimentacao();
		
        
        Date dataFutura = dataUtil.obterDataComDiferencaDias(5);
        
        
		mPage.selecionarTipoMovimentacao("Receita");
		mPage.setDataMovimentacao(dataUtil.obterdataFormatada(dataFutura));
		mPage.setDataPagamento(dataUtil.obterdataFormatada(dataFutura));
		mPage.setDescricao("pagamento cliente");
		mPage.setInteressado("fulano");
		mPage.setValor("200");
		mPage.selecionaConta("Conta testada");
		mPage.setStatusPago();
		mPage.salvar();
		
		Assert.assertEquals("Data da Movimentação deve ser menor ou igual à data atual", mPage.obterMensagemErro());
	}
	
	@Test
	public void removerMovimentacao() {
		menuPage.resumoMensal();
		
		
		
	}
}
