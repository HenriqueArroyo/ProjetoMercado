package Model;

public class Funcionarios {
    

    private String nome;
    private String cpf;
    private String senha;
    private String idade;
    private String telefone;
    private String email;
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
    public String getIdade() {
        return idade;
    }
    public void setIdade(String idade) {
        this.idade = idade;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Funcionarios(String nome, String cpf, String senha, String idade, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.idade = idade;
        this.telefone = telefone;
        this.email = email;
    }
    public void add(Funcionarios funcionarios) {
    }

    




}
