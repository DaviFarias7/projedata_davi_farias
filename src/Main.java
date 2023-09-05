import entities.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> listaFuncionarios = new ArrayList<>();

        LocalDate dtNascF1 = LocalDate.of(2000, 10, 18);
        LocalDate dtNascF2 = LocalDate.of(1990, 5, 12);
        LocalDate dtNascF3 = LocalDate.of(1961, 5, 2);
        LocalDate dtNascF4 = LocalDate.of(1988, 10, 14);
        LocalDate dtNascF5 = LocalDate.of(1995, 01, 5);
        LocalDate dtNascF6 = LocalDate.of(1999, 11, 19);
        LocalDate dtNascF7 = LocalDate.of(1993, 3, 31);
        LocalDate dtNascF8 = LocalDate.of(1994, 7, 8);
        LocalDate dtNascF9 = LocalDate.of(2003, 5, 24);
        LocalDate dtNascF10 = LocalDate.of(1996, 9, 2);

        Funcionario f1 = new Funcionario("Maria", dtNascF1, new BigDecimal(200.44), "Operador");
        Funcionario f2 = new Funcionario("Joao", dtNascF2, new BigDecimal(2284.38), "Operador");
        Funcionario f3 = new Funcionario("Caio", dtNascF3, new BigDecimal(9836.14), "Coordenador");
        Funcionario f4 = new Funcionario("Miguel", dtNascF4, new BigDecimal(19119.88), "Diretor");
        Funcionario f5 = new Funcionario("Alice", dtNascF5, new BigDecimal(2234.68), "Recepcionista");
        Funcionario f6 = new Funcionario("Heitor", dtNascF6, new BigDecimal(1582.72), "Operador");
        Funcionario f7 = new Funcionario("Arthur", dtNascF7, new BigDecimal(4071.84), "Contador");
        Funcionario f8 = new Funcionario("Laura", dtNascF8, new BigDecimal(3017.45), "Gerente");
        Funcionario f9 = new Funcionario("Heloisa", dtNascF9, new BigDecimal(1606.85), "Eletricista");
        Funcionario f10 = new Funcionario("Helena", dtNascF10, new BigDecimal(2799.93), "Gerente");

        System.out.println("3.1 - Inserir todos os funcionários, na mesma ordem e informações da tabela");
        listaFuncionarios.addAll(List.of(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10));

        System.out.println("3.2 – Remover o funcionário “João” da lista.");
        listaFuncionarios.remove(f2);

        System.out.println("3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:\n"
                + " • informação de data deve ser exibido no formato dd/mm/aaaa;");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println(" • informação de valor numérico deve ser exibida no formatado com separador de "
                + "milhar como ponto e decimal como vírgula.");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        String salarioFormatado = df.format(0);

        for (Funcionario funcionario : listaFuncionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getNascimento().format(formatter));
            System.out.println("Salário: " + df.format(funcionario.getSalario()));
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();

        }

        System.out.println(
                "3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.");
        for (Funcionario funcNovoSal : listaFuncionarios) {
            BigDecimal salarioAtualizado = funcNovoSal.getSalario().multiply(new BigDecimal("1.10"));
            funcNovoSal.setSalario(salarioAtualizado);
            System.out.println("Nome: " + funcNovoSal.getNome());
            System.out.println("Salário Atualizado: " + df.format(salarioAtualizado));
        }

        System.out.println(
                "3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.");
        Map<String, List<Funcionario>> funcionariosPorFuncao = listaFuncionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("3.6 – Imprimir os funcionários, agrupados por função.");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            String funcao = entry.getKey();
            List<Funcionario> funcionariosDaFuncao = entry.getValue();

            System.out.println("Funcionários da função: " + funcao);
            for (Funcionario funcionario : funcionariosDaFuncao) {
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("Data de Nascimento: " + funcionario.getNascimento().format(formatter));
                System.out.println("Salário Atualizado: " + df.format(funcionario.getSalario()));
                System.out.println("Função: " + funcionario.getFuncao());
                System.out.println();
            }
        }

        System.out.println("3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.");
        for (Funcionario funcionario : listaFuncionarios) {
            Month mesAniversario = funcionario.getNascimento().getMonth();
            if (mesAniversario == Month.OCTOBER || mesAniversario == Month.DECEMBER) {
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("Data de Nascimento: " + funcionario.getNascimento().format(formatter));
                System.out.println("Salário: " + df.format(funcionario.getSalario()));
                System.out.println("Função: " + funcionario.getFuncao());
                System.out.println();
            }
        }

        System.out.println("3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.");
        int maiorIdade = Integer.MIN_VALUE;
        Funcionario funcionarioMaiorIdade = null;

        for (Funcionario funcionario : listaFuncionarios) {
            LocalDate dataNascimento = funcionario.getNascimento();
            LocalDate dataAtual = LocalDate.now();
            int idade = Period.between(dataNascimento, dataAtual).getYears();

            if (idade > maiorIdade) {
                maiorIdade = idade;
                funcionarioMaiorIdade = funcionario;
            }

        }

        if (funcionarioMaiorIdade != null) {
            System.out.println("Funcionário mais velho:");
            System.out.println("Nome: " + funcionarioMaiorIdade.getNome());
            System.out.println("Idade: " + maiorIdade);
        } else {
            System.out.println("Não foi possível encontrar o funcionário mais velho.");
        }

        System.out.println("3.10 – Imprimir a lista de funcionários por ordem alfabética.");
        Comparator<Funcionario> comparadorPorNome = new Comparator<Funcionario>() {
            @Override
            public int compare(Funcionario f1, Funcionario f2) {
                return f1.getNome().compareTo(f2.getNome());
            }
        };

        Collections.sort(listaFuncionarios, comparadorPorNome);

        for (Funcionario funcionario : listaFuncionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getNascimento().format(formatter));
            System.out.println("Salário: " + df.format(funcionario.getSalario()));
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();
        }

        System.out.println("3.11 – Imprimir o total dos salários dos funcionários.");
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : listaFuncionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        System.out.println("Total dos Salários: " + df.format(totalSalarios));

        System.out.println("3.12 – Imprimir quantos salários mínimos ganha cada funcionário, " +
                "considerando que o salário mínimo é R$1212.00.");
        for (Funcionario funcionario : listaFuncionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getNascimento().format(formatter));
            System.out.println("Salário: " + df.format(funcionario.getSalario()));
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();

            BigDecimal salarioMinimo = new BigDecimal("1212.00");

            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("O(a) funcionário(a) " + funcionario.getNome() + " recebe "
                    + df.format(salariosMinimos) + " salários Mínimos: ");
            System.out.println();

        }
    }
}