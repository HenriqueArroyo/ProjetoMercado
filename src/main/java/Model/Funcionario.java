package Model;

public class Funcionario {
    

    private String nome;
    private String cpf;
    private String senha;
    private String dataNascimento;
    private String telefone;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getdataNascimento() {
        return dataNascimento;
    }
    public void setdataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Funcionario(String nome, String cpf, String senha, String dataNascimento, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
       
    }
    public void add(Funcionario funcionarios) {
    }

    




}
