import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class Application {
    public final Scanner ler = new Scanner(System.in);
    public final Connection conn;
    public final Statement st;

    
    public Application (Connection conn, Statement st) {
        this.conn = conn;
        this.st = st;
    }
    
    public void clear() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException e) {
            System.err.format("Erro ao limpar a tela: %s", e.getMessage());
        }
    }
    
    
    public void FrontEnd () {
        int escolha;
        while (true) {
            clear();
            System.out.println("\n---------- SEJA BEM-VINDO A LISTA DE DESEJOS ----------");
            System.out.println(" \n     OBS:Primeiro você precisa criar uma lista.\n");
            System.out.println("    1 - Criar lista nova.");
            System.out.println("    2 - Inserir dados em uma lista.");
            System.out.println("    3 - ler dados de uma lista.");
            System.out.println("    4 - Atualizar dados de uma lista.");
            System.out.println("    5 - Deletar uma lista.");
            System.out.println("    0 - Sair.");
            System.out.print("\nEscolha: ");
            escolha = ler.nextInt();
            clear();
            switch (escolha) {
                case 1:
                    this.criar_lista();
                    break;
                case 2:
                    this.inserir_dados();
                    break;
                case 3:
                    this.ler_dados();
                    break;
                case 4:
                    this.atualizar_dados();
                    break;
                case 5:
                    this.deletar_lista(";");
                    break;
                case 0:
                    System.out.println("Adeus!");
                    return;
                default:
                    System.out.println("Erro.");
                    break;
            }
        }
    }
    
    
    public void criar_lista() {
        try {
            System.out.println("---------- CRIAR NOVA LISTA ----------");
            System.out.print("\nNome da lista: ");
            String nomeTabela = ler.next();
            String SQLCriarTabela = "CREATE TABLE " + nomeTabela + " (id SERIAL PRIMARY KEY, preco float, nome VARCHAR(60), tipo VARCHAR(60));";
            st.executeUpdate(SQLCriarTabela);
            System.out.print("\nCriando lista");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            clear();
            this.inserir_dados();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (InterruptedException e) {
            System.err.format("InterruptedException: %s", e.getMessage());
        }
    }

    
    public void inserir_dados() {
        try {
            System.out.println("---------- LISTAS DISPONIVEIS ----------\n");

            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(conn.getCatalog(), null, null, new String[]{"TABLE"});

            // Iterar sobre os resultados e imprimir os nomes das tabelas
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName + "\n");
            }
            System.out.print("\nDigite o nome da lista que quer inserir dados:");
            String nomeTabela = ler.next();
            clear();
            System.out.println("---------- INSERIR DADOS ----------");
            System.out.print("\nNome do item: ");
            String nome = ler.next();
            System.out.print("\nPreço do item: ");
            float preco = ler.nextFloat();
            String SQLInserirDados = "INSERT INTO " + nomeTabela + " (preco, nome) VALUES (" + preco + ", '" + nome + "')";
            st.executeUpdate(SQLInserirDados);
            clear();
            System.out.print("\ninserindo dados");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (InterruptedException e) {
            System.err.format("InterruptedException: %s", e.getMessage());
        }
    }
    
    public void ler_dados() {
        ResultSet result;
        try {
            System.out.println("---------- LISTAS DISPONIVEIS ----------\n");

            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(conn.getCatalog(), null, null, new String[]{"TABLE"});

            // Iterar sobre os resultados e imprimir os nomes das tabelas
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName + "\n");
            }
            System.out.print("\nDigite o nome da lista que quer ler:");
            String nomeTabela = ler.next();
            clear();
            String SQLLerDados = "SELECT * FROM " + nomeTabela;
            result = st.executeQuery(SQLLerDados);
            while (result.next()) {
                System.out.println("--------------------------------------------------");
                System.out.println("nome: " + result.getString("nome"));
                System.out.println("preco: " + result.getFloat("preco"));
            }
            result.close();
            System.out.println("--------------------------------------------------");
            System.out.print("\nleia dados");
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);
            System.out.print(".");
            Thread.sleep(2000);        
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (InterruptedException e) {
            System.err.format("InterruptedException: %s", e.getMessage());
        }
    
    }
    public void atualizar_dados() {
        try {
            System.out.println("---------- LISTAS DISPONIVEIS ----------\n");

            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(conn.getCatalog(), null, null, new String[]{"TABLE"});

            // Iterar sobre os resultados e imprimir os nomes das tabelas
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName + "\n");
            }

            System.out.print("Digite o nome da tabela que deseja atualizar: ");
            String nomeTabela = ler.next();
            clear();

            System.out.println("---------- DADOS ATUAIS ----------\n\n");
            String SQLLerDados = "SELECT * FROM " + nomeTabela;
            ResultSet result = st.executeQuery(SQLLerDados);
            while (result.next()) {
                System.out.println("ID: " + result.getInt("ID"));
                System.out.println("nome: " + result.getString("nome"));
                System.out.println("preco: " + result.getFloat("preco"));
                System.out.println("--------------------------------------------------");

            }
            result.close();

            System.out.print("\nDigite o ID da linha que deseja atualizar: ");
            int idLinha = ler.nextInt();
            clear();
            System.out.println("---------- ATUALIZAR LISTA ----------\n");
            System.out.print("Digite o nome da coluna a ser atualizada(nome ou preco): ");
            String nomeColuna = ler.next();

            System.out.print("\nDigite a atualizaçao: ");
            String novoValor = ler.next();

            String SQLAtualizarDados = "UPDATE " + nomeTabela + " SET " + nomeColuna + " = \'" + novoValor + "\' WHERE ID = " + idLinha;
            st.executeUpdate(SQLAtualizarDados);
            clear();
            System.out.print("\nDados atualizados com sucesso");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }catch (InterruptedException e) {
            System.err.format("InterruptedException: %s", e.getMessage());
        }
    }


    public void deletar_lista(String string) {

        try {
            System.out.println("---------- DELETAR LISTA ----------\n");

            System.out.print("listas disponíveis:\n\n");

            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(conn.getCatalog(), null, null, new String[]{"TABLE"});

            
            // Iterar sobre os resultados e imprimir os nomes das tabelas
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName+"\n");
            }
            System.out.print("\nDigite o nome da lista que quer excluir:");
            String tabela = ler.next(); 
            String SQLdeletarDados = "DROP TABLE "+ tabela+ ";";
            st.executeUpdate(SQLdeletarDados);
            clear();
            System.out.print("\nDeletado com sucesso");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
            System.out.print(".");
            Thread.sleep(700);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }catch (InterruptedException e) {
            System.err.format("InterruptedException: %s", e.getMessage());
        }
    
    }
}