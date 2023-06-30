import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Cliente> clientes = new ArrayList<>();
    private static Cliente clienteLogado;

    public static void main(String[] args) {
        exibirMenuPrincipal();
    }

    private static void exibirMenuPrincipal() {
        boolean sair = false;
        while (!sair) {
            System.out.println("=== Banco Digital ===");
            System.out.println("1. Criar conta");
            System.out.println("2. Acessar conta");
            System.out.println("0. Sair");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    acessarConta();
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void criarConta() {
    System.out.print("Digite o nome do cliente: ");
    String nome = scanner.nextLine();

    System.out.print("Digite o número da conta: ");
    int numeroConta = scanner.nextInt();
    scanner.nextLine();

    Cliente cliente = new Cliente(nome, numeroConta);
    Conta conta = new ContaCorrente(cliente);
    cliente.setConta(conta);
    clientes.add(cliente);

    System.out.println("Conta criada com sucesso. Número da conta: " + cliente.getNumeroConta());
    System.out.println();
}

    private static void acessarConta() {
        System.out.print("Digite o número da conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = encontrarCliente(numeroConta);
        if (cliente != null) {
            clienteLogado = cliente;
            exibirMenuConta();
        } else {
            System.out.println("Conta não encontrada.");
            System.out.println();
        }
    }

    private static void exibirMenuConta() {
        boolean sair = false;
        while (!sair) {
            System.out.println("=== Menu Conta ===");
            System.out.println("1. Sacar");
            System.out.println("2. Depositar");
            System.out.println("3. Transferir");
            System.out.println("4. Imprimir Extrato");
            System.out.println("0. Logout");
            System.out.print("Opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    sacar();
                    break;
                case 2:
                    depositar();
                    break;
                case 3:
                    transferir();
                    break;
                case 4:
                    imprimirExtrato();
                    break;
                case 0:
                    clienteLogado = null;
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void sacar() {
        System.out.print("Digite o valor do saque: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        Conta conta = clienteLogado.getConta();
        double saldoAtual = conta.getSaldo();

    if (valor > 0 && valor <= saldoAtual) {
        conta.sacar(valor);
        System.out.println("Saque realizado com sucesso. Novo saldo: " + conta.getSaldo());
        System.out.println();
    } else {
        System.out.println("Valor inválido ou saldo insuficiente. O saque não foi realizado.");
        System.out.println();
    }
}


    private static void depositar() {
        System.out.print("Digite o valor do depósito: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor > 0) {
            Conta conta = clienteLogado.getConta();
            conta.depositar(valor);
            System.out.println("Depósito realizado com sucesso. Novo saldo: " + conta.getSaldo());
            System.out.println();
        } else {
            System.out.println("Valor inválido. O depósito não foi realizado.");
            System.out.println();
        }
    }

    private static void transferir() {
        System.out.print("Digite o valor da transferência: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (valor > 0) {
            System.out.print("Digite o número da conta de destino: ");
            int numeroContaDestino = scanner.nextInt();
            scanner.nextLine();

            Cliente clienteDestino = encontrarCliente(numeroContaDestino);
            if (clienteDestino != null) {
                Conta contaOrigem = clienteLogado.getConta();
                Conta contaDestino = clienteDestino.getConta();
                contaOrigem.transferir(valor, contaDestino);
                System.out.println("Transferência realizada com sucesso.");
                System.out.println("Novo saldo da conta origem: " + contaOrigem.getSaldo());
                System.out.println("Novo saldo da conta destino: " + contaDestino.getSaldo());
                System.out.println();
            } else {
                System.out.println("Conta de destino não encontrada.");
                System.out.println();
            }
        } else {
            System.out.println("Valor inválido. A transferência não foi realizada.");
            System.out.println();
        }
    }

    private static void imprimirExtrato() {
        Conta conta = clienteLogado.getConta();
        conta.imprimirExtrato();
        System.out.println();
    }

    private static Cliente encontrarCliente(int numeroConta) {
        for (Cliente cliente : clientes) {
            if (cliente.getNumeroConta() == numeroConta) {
                return cliente;
            }
        }
        return null;
    }
}
