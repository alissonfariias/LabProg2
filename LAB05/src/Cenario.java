import java.util.ArrayList;
import java.util.List;
import enums.Estado;

public class Cenario {

	private int numeracao;
	private String descricao;
	private List<Aposta> apostas;
	private Estado estado;

	public Cenario(String descricao, int numeracao) {
		this.numeracao = numeracao;
		this.descricao = "";
		this.estado = Estado.NAO_FINALIZADO;
		this.apostas = new ArrayList<>();
	}

	public String getDescricao() {
		return this.descricao;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void cadastraAposta(String nomeApostador, int valorAposta, String previsao) {
		Aposta aposta = new Aposta(nomeApostador, valorAposta, previsao);
		this.apostas.add(aposta);
	}

	public int valorTotalDeApostas() {
		int valorTotal = 0;
		for (Aposta aposta : this.apostas) {
			valorTotal += aposta.getValor();
		}
		return valorTotal;
	}

	public int totalDeApostas(int cenario) {
		return this.apostas.size();
	}

	public String exibeApostas(int cenario) {
		String listagem = "";
		for (Aposta aposta : apostas) {
			listagem += aposta.toString() + System.lineSeparator();
		}
		return listagem;
	}

	@Override
	public String toString() {
		return this.numeracao + " - " + this.getDescricao() + " - " + this.getEstado();
	}

	public void fechaAposta(int cenario, boolean ocorreu) {
		if (ocorreu) {
			this.estado = Estado.FINALIZADO_OCORREU;
		} else {
			this.estado = Estado.FINALIZADO_N_OCORREU;
		}
	}
	
	public int somaApostasPerdedoras(int cenario) {
		int soma = 0;
		if (this.estado.equals(Estado.FINALIZADO_N_OCORREU)) {
			for (Aposta aposta : apostas) {
				if (aposta.getPrevisao().equals("N VAI ACONTECER")) {
					soma += aposta.getValor();
				}
			}
		} else if (this.estado.equals(Estado.FINALIZADO_OCORREU)) {
			for (Aposta aposta : apostas) {
				if (aposta.getPrevisao().equals("VAI ACONTECER")) {
					soma += aposta.getValor();
				}
			}
		} else {
			throw new IllegalArgumentException("Cenário não finalizado.");
		}
		return soma;
	}
	
	public int valorCaixaCenario(double taxa, int cenario) {
		return (int) Math.floor(this.somaApostasPerdedoras(cenario) * taxa);
	}
	
	public int valorRateio(double taxa, int cenario) {
		int valorRateio = somaApostasPerdedoras(cenario) - valorCaixaCenario(taxa, cenario);
		return valorRateio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numeracao;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cenario other = (Cenario) obj;
		if (numeracao != other.numeracao)
			return false;
		return true;
	}
	
}
