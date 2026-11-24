package com.assistencia.decorator;

import com.assistencia.model.valor.Dinheiro;
import static com.assistencia.decorator.TipoAdicional.BACKUP_DADOS;

/**

 * [Decorator - Aula 12] ConcreteDecorator: Backup dos dados do cliente antes do reparo. Acrescenta sua

 * descrição e seu valor ao serviço decorado (como Leite, Chocolate e Chantilly).

 */
public class BackupDados extends AdicionalDecorator {
    public BackupDados(ServicoOrcamento servico) {
        super(servico);
    }

    @Override
    public String descrever() {
        String anterior = servico.descrever();
        return BACKUP_DADOS.anexarDescricao(anterior);
    }

    @Override
    public Dinheiro calcularCusto() {
        Dinheiro anterior = servico.calcularCusto();
        return BACKUP_DADOS.acrescentarValor(anterior);
    }
}
