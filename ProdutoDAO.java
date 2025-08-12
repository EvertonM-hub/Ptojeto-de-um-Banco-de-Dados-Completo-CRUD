import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
	private final Connection CONEXAO_DB; // Variavel membro

	//Construtor que inicializa a conexao com o banco de dados
	public ProdutoDAO(Connection conexao) {
		this.CONEXAO_DB = conexao;
	}

	//Metod para inserir produto novo no banco de dados
	public void inserir(Produto produto) {
		String sql = "INSERT INTO produtos ( nome_produto, quantidade, preco, status) VALUES (?, ?, ?, ?)"; // preparedStatement, são valores flexiveis para o BD.
		try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getPreco());
			stmt.setString(4, produto.getStatus());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Erro ao inserir produto: " + e.getMessage());
		}
	}

	// Metodo para excluir todos os produtos do BD
		public void excluirTodos() {
			String sql = "DELETE FROM produto";
			try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {
				stmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Erro ao excluir todos os produtos: " + e.getMessage());
			}
		}

		//Metodo consultar um produto por ID
			public Produto consultarPorId(int id) {
				String sql = "SELECT * FROM produtos WHERE id_produto = ?"; // comando
				try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {
					stmt.setInt(1, id); // Definindo o parametro do ID antes de executar a consulta
					try (ResultSet rs = stmt.executeQuery()) { // execução do camando
						if (rs.next()) { // se tiver o produto procurado entra no if
							Produto produto = new Produto();
							produto.setId(rs.getInt("id_produto"));
							produto.setNome(rs.getString("nome_produto"));
							produto.setQuantidade(rs.getInt("quantidade"));
							produto.setPreco(rs.getDouble("preco"));
							produto.setStatus(rs.getString("status"));
							return produto;
						}	
					}	

				} catch (SQLException e) {
					System.err.println("Erro ao cunsultar produto por id: " + e.getMessage());
				}
				return null;
			}

			//Metodo para atualizar informacoes de um produto no BD
			public void atualizar(Produto produto) {
				String sql = "UPDATE produtos SET nome_produto = ?, quantidade = ?, preco = ?, status = ?, WHERE id_produto = ?";
				try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {
					stmt.setString(1, produto.getNome());
					stmt.setInt(2, produto.getQuantidade());
					stmt.setDouble(3, produto.getPreco());
					stmt.setString(4, produto.getStatus());
					stmt.setInt(5, produto.getId());
					stmt.executeUpdate();
				} catch (SQLException e) {
					System.err.println("Erro ao atualizar produto: " + e.getMessage());
				}
			}

			// Metodo para excluir um produto pelo ID
			public void excluir(int id) {
				String sql = "DELETE FROM produtos WHERE id_produto = ?";
					try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql)) {
						stmt.setInt(1, id);
						stmt.executeUpdate();
					} catch (SQLException e) {
						System.err.println("Erro ao deletar um produto: " + e.getMessage());
					}
 			}

 			//Metodo para listar todos os produtos do BD
 			public List<Produto> listarTodos() {
 				List<Produto> produtos = new ArrayList<>(); //colections
 				String sql = "SELECT * FROM produtos";
 				try (PreparedStatement stmt = CONEXAO_DB.prepareStatement(sql);
 					ResultSet rs = stmt.executeQuery()) {
 					while (rs.next()) { //roda ate listar todos os produtos, laço while
 						Produto produto = new Produto();
 						produto.setId(rs.getInt("id_produto"));
						produto.setNome(rs.getString("nome_produto"));
						produto.setQuantidade(rs.getInt("quantidade"));
						produto.setPreco(rs.getDouble("preco"));
						produto.setStatus(rs.getString("status"));
						produtos.add(produto);
 					}
 				} catch (SQLException e) {
 					System.err.println("Erro ao listar produtos: " + e.getMessage());
 				}
 				return produtos; 
 			}


}