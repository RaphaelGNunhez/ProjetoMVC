package br.edu.fatecgru.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import br.edu.fatecgru.dao.AlunoDAO;
import br.edu.fatecgru.model.Aluno;
import br.edu.fatecgru.util.ExportadorPDF;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.InputEvent;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JFormattedTextField;

public class JanelaCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_Boletim;
	private JComboBox comboUFDados;
	private JComboBox comboBoxCampus;
	private JComboBox comboBoxCurso;
	private JComboBox comboBoxDisciplina;
	private JComboBox comboBoxPeriodo;
	private JComboBox comboBoxSemestre;
	private JComboBox comboBoxNota;
	
	// No início da classe, junto com os outros campos
	private JFormattedTextField txtRgmNotas;
	private JFormattedTextField txtDataNascimento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCadastro frame = new JanelaCadastro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	// Função auxiliar para redimensionar ícones
			private ImageIcon redimensionarIcone(String caminho, int largura, int altura) {
			    ImageIcon iconeOriginal = new ImageIcon(JanelaCadastro.class.getResource(caminho));
			    Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
			    return new ImageIcon(imagemRedimensionada);
			}
			
			
			private void aplicarComportamentoCliqueInicial(JFormattedTextField campo, char placeholderChar) {
			    campo.addMouseListener(new java.awt.event.MouseAdapter() {
			        @Override
			        public void mousePressed(java.awt.event.MouseEvent e) {
			            String texto = campo.getText().replace(String.valueOf(placeholderChar), "").trim();
			            if (texto.isEmpty()) {
			                campo.setCaretPosition(0);
			            }
			        }
			    });
			}

	/**
	 * Create the frame.
	 */
				
			
		public JanelaCadastro() throws Exception {
			
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/escolhinha.png"));
		setIconImage(icon.getImage());
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 497);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		menuBar.setMinimumSize(new Dimension(11, 11));
		menuBar.setSize(new Dimension(0, 28));
		menuBar.setBackground(new Color(0, 128, 192));
		menuBar.setMargin(new Insets(6, 4, 6, 4));
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Aluno");
		mnNewMenu.setForeground(new Color(255, 255, 255));
		mnNewMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("Notas e Faltas");
		mnNewMenu_1.setForeground(new Color(255, 255, 255));
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnNewMenu_1);
		
		JMenu mnNewMenu_2 = new JMenu("Ajuda");
		mnNewMenu_2.setForeground(new Color(255, 255, 255));
		mnNewMenu_2.setFont(new Font("Segoe UI", Font.BOLD, 14));
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Sobre");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		        // Carrega a imagem corretamente a partir do classpath
		        ImageIcon icon = new ImageIcon(getClass().getResource("/imagens/projetoMVC.png"));
		        JLabel labelImage = new JLabel(icon);
		        panel.add(labelImage);

		        // Adiciona os nomes dos participantes
		        panel.add(Box.createVerticalStrut(10)); // Espaço
		        panel.add(new JLabel("Participantes do grupo:"));
		        panel.add(new JLabel("• Danilo Zamai | Lucas Silva Contieri | Mateus Picoli | Priscila Diniz | Raphael Godek Nunhez "));
		        /*panel.add(new JLabel("• Lucas Silva Contieri"));
		        panel.add(new JLabel("• Mateus Picoli"));
		        panel.add(new JLabel("• Priscila Diniz"));
		        panel.add(new JLabel("• Raphael Godek Nunhez"));*/

		        JOptionPane.showMessageDialog(null, panel, "Sobre", JOptionPane.INFORMATION_MESSAGE);
		    }
		});
		
		mntmNewMenuItem_9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UNDEFINED, 0));
		mntmNewMenuItem_9.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnNewMenu_2.add(mntmNewMenuItem_9);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(10, 15));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(37, 40, 577, 366);
		contentPane.add(tabbedPane);
		
		JPanel panelDadosPessoais = new JPanel();
		panelDadosPessoais.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Dados pessoais", null, panelDadosPessoais, null);
		panelDadosPessoais.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(30, 34, 46, 14);
		panelDadosPessoais.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("RGM:");
		lblNewLabel_1.setBounds(354, 34, 46, 14);
		panelDadosPessoais.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Data de Nascimento:");
		lblNewLabel_2.setBounds(30, 82, 118, 14);
		panelDadosPessoais.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("CPF:");
		lblNewLabel_3.setBounds(328, 82, 46, 14);
		panelDadosPessoais.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Email:");
		lblNewLabel_4.setBounds(30, 136, 46, 14);
		panelDadosPessoais.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Endereço:");
		lblNewLabel_5.setBounds(30, 191, 68, 14);
		panelDadosPessoais.add(lblNewLabel_5);
		
		
		JLabel lblNewLabel_6 = new JLabel("Município:");
		lblNewLabel_6.setBounds(30, 249, 55, 14);
		panelDadosPessoais.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("UF:");
		lblNewLabel_7.setBounds(237, 249, 25, 14);
		panelDadosPessoais.add(lblNewLabel_7);
		
		String[] estados = {
			    "","AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
			    "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
			    "RS", "RO", "RR", "SC", "SP", "SE", "TO"
			};
		comboUFDados = new JComboBox<>(estados);
		comboUFDados.setBounds(264, 245, 54, 22);
		panelDadosPessoais.add(comboUFDados);
		
		JLabel lblNewLabel_8 = new JLabel("Telefone:");
		lblNewLabel_8.setBounds(341, 249, 46, 14);
		panelDadosPessoais.add(lblNewLabel_8);
		
		// NOME
		MaskFormatter nomeMask = new MaskFormatter("*****************************************************************");
		nomeMask.setValidCharacters("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ");
		JFormattedTextField txtNomeDados = new JFormattedTextField(nomeMask);
		txtNomeDados.setBounds(86, 31, 244, 22);
		txtNomeDados.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		txtNomeDados.setFocusLostBehavior(JFormattedTextField.PERSIST);
		panelDadosPessoais.add(txtNomeDados);
		aplicarComportamentoCliqueInicial(txtNomeDados, '*');

		// RGM
		MaskFormatter rgmMask = new MaskFormatter("############");
		rgmMask.setValidCharacters("0123456789");
		JFormattedTextField txtRgmDados = new JFormattedTextField(rgmMask);
		txtRgmDados.setBounds(392, 31, 150, 22);
		txtRgmDados.setFocusLostBehavior(JFormattedTextField.PERSIST);
		panelDadosPessoais.add(txtRgmDados);
		aplicarComportamentoCliqueInicial(txtRgmDados, '#');

		// EMAIL (sem máscara, não precisa aplicar)
		JFormattedTextField txtEmailDados = new JFormattedTextField();
		txtEmailDados.setBounds(95, 133, 447, 22);
		panelDadosPessoais.add(txtEmailDados);

		// ENDEREÇO (sem máscara, não precisa aplicar)
		JFormattedTextField txtEnderecoDados = new JFormattedTextField();
		txtEnderecoDados.setBounds(95, 188, 447, 22);
		panelDadosPessoais.add(txtEnderecoDados);

		// MUNICÍPIO
		MaskFormatter municipioMask = new MaskFormatter("*********************************");
		municipioMask.setValidCharacters("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ");
		JFormattedTextField txtMuniDados = new JFormattedTextField(municipioMask);
		txtMuniDados.setBounds(95, 246, 116, 22);
		txtMuniDados.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		txtRgmDados.setFocusLostBehavior(JFormattedTextField.PERSIST);
		panelDadosPessoais.add(txtMuniDados);
		aplicarComportamentoCliqueInicial(txtMuniDados, '*');

		// TELEFONE
		JFormattedTextField txtTeleDados = new JFormattedTextField(new MaskFormatter("(##)#####-####"));
		txtTeleDados.setBounds(397, 245, 145, 22);
		txtTeleDados.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		txtRgmDados.setFocusLostBehavior(JFormattedTextField.PERSIST);
		panelDadosPessoais.add(txtTeleDados);
		aplicarComportamentoCliqueInicial(txtTeleDados, '#');

		// DATA DE NASCIMENTO
		JFormattedTextField txtDataNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
		txtDataNasc.setBounds(150, 80, 150, 21);
		txtDataNasc.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		txtRgmDados.setFocusLostBehavior(JFormattedTextField.PERSIST);
		panelDadosPessoais.add(txtDataNasc);
		aplicarComportamentoCliqueInicial(txtDataNasc, '#');

		// CPF
		JFormattedTextField txtCpfDados = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		txtCpfDados.setBounds(371, 80, 171, 21);
		txtCpfDados.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		txtRgmDados.setFocusLostBehavior(JFormattedTextField.PERSIST);
		panelDadosPessoais.add(txtCpfDados);
		aplicarComportamentoCliqueInicial(txtCpfDados, '#');
		
		
		
		//Tela Curso
		JPanel panelCurso = new JPanel();
		panelCurso.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Curso", null, panelCurso, null);
		panelCurso.setLayout(null);
		
		JLabel lblNewLabel_9 = new JLabel("Curso");
		lblNewLabel_9.setBounds(44, 44, 39, 14);
		panelCurso.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Campus");
		lblNewLabel_10.setBounds(44, 100, 62, 14);
		panelCurso.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Período");
		lblNewLabel_11.setBounds(44, 162, 46, 14);
		panelCurso.add(lblNewLabel_11);
		
		String[] cursos = {
			    "","Análise e Desenvolvimento de Sistemas", "Engenharia de Software", "Logística", "Logística Aeroportuária",
			    "Mecatrônica"
			};
		comboBoxCurso = new JComboBox<>(cursos);
		comboBoxCurso.setBounds(129, 40, 379, 22);
		panelCurso.add(comboBoxCurso);
		
		String[] campus = {
			    "","Adamantina-SP", "Araçatuba-SP", "Birigui-SP", "Campinas-SP", "Diadema-SP", "Fernandópolis-SP", "Guarulhos-SP", "Marília-SP", "Pirituba-SP", 
			    "Santos-SP", "São Paulo-SP", "Taubaté", 
			};
		comboBoxCampus = new JComboBox(campus);
		comboBoxCampus.setBounds(129, 96, 379, 22);
		panelCurso.add(comboBoxCampus);
		
		// Criando os botões de turno
		JRadioButton rdbtnMatutino = new JRadioButton("Matutino");
		rdbtnMatutino.setBounds(129, 158, 83, 23);
		panelCurso.add(rdbtnMatutino);

		JRadioButton rdbtnVespertino = new JRadioButton("Vespertino");
		rdbtnVespertino.setBounds(245, 158, 100, 23); // Largura ajustada
		panelCurso.add(rdbtnVespertino);

		JRadioButton rdbtnNoturno = new JRadioButton("Noturno");
		rdbtnNoturno.setBounds(380, 158, 83, 23);
		panelCurso.add(rdbtnNoturno);

		// Agrupando os botões (para permitir apenas uma seleção)
		ButtonGroup grupoTurnos = new ButtonGroup();
		grupoTurnos.add(rdbtnMatutino);
		grupoTurnos.add(rdbtnVespertino);
		grupoTurnos.add(rdbtnNoturno);
		
		

		// Botão Salvar tela Curso
		
		JButton btnSalvarCurso = new JButton("");

		btnSalvarCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

	        	
	        	try {
		            Aluno aluno = new Aluno();
		            aluno.setNome(txtNomeDados.getText());
		            aluno.setRGM(txtRgmDados.getText());
		            aluno.setDataNasc(txtDataNasc.getText());
		            aluno.setCPF(txtCpfDados.getText());
		            aluno.setEmail(txtEmailDados.getText());
		            aluno.setEndereco(txtEnderecoDados.getText());
		            aluno.setMunicipio(txtMuniDados.getText());
		            aluno.setUF(comboUFDados.getSelectedItem().toString());
		            aluno.setTelefone(txtTeleDados.getText());
		            aluno.setCurso(comboBoxCurso.getSelectedItem().toString());
		            aluno.setCampus(comboBoxCampus.getSelectedItem().toString());
		            aluno.setSemestre("2020-1");  // Semestre padrão

		            // Verifica o turno selecionado
		            String turnoSelecionado = "";
		            if (rdbtnMatutino.isSelected()) {
		                turnoSelecionado = "Matutino";
		            } else if (rdbtnVespertino.isSelected()) {
		                turnoSelecionado = "Vespertino";
		            } else if (rdbtnNoturno.isSelected()) {
		                turnoSelecionado = "Noturno";
		            } else {
		                throw new Exception("Por favor, selecione um turno.");
		            }
					 if (txtNomeDados.getText().equals("") ||txtRgmDados.getText().equals("") || txtCpfDados.getText().equals("   .   .   -  ")|| txtDataNasc.getText().equals("")||txtEmailDados.getText().equals("")||
				        		txtTeleDados.getText().equals("")||txtEnderecoDados.getText().equals("") ||
				        		txtMuniDados.getText().equals("")||comboUFDados.getSelectedItem().equals("UF")||
				        		comboBoxCurso.getSelectedItem().equals("")||
				        		comboBoxCampus.getSelectedItem().equals("")|| rdbtnMatutino.isSelected() == false && rdbtnVespertino.isSelected() == false && rdbtnNoturno.isSelected() == false) {
				        	JOptionPane.showMessageDialog(null, "Nada pode ser nulo");
				        	
				        } else {

		            aluno.setPeriodo(turnoSelecionado);

		            // Salvar no banco
		            AlunoDAO dao = new AlunoDAO();
		            dao.salvar(aluno);

		            JOptionPane.showMessageDialog(null, "Aluno salvo com sucesso!");
				        }
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao salvar aluno: " + ex.getMessage());
		        }
	        	
	        }
		
});


		btnSalvarCurso.setIcon(redimensionarIcone("/imagens/salvararquivo.png", 32, 32));
		btnSalvarCurso.setBounds(30, 236, 89, 62);
		panelCurso.add(btnSalvarCurso);

		// Botão Atualizar Tela Curso
		JButton btnAtualizarCurso = new JButton("");
		btnAtualizarCurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
			                // Limpar campos da interface
			                txtRgmDados.setText("");
			                txtNomeDados.setText("");
			                txtCpfDados.setText("");
			                txtEmailDados.setText("");
			                txtEnderecoDados.setText("");
			                txtMuniDados.setText("");
			                comboUFDados.setSelectedIndex(-1);
			                txtTeleDados.setText("");
			                txtDataNasc.setText("");
			                comboBoxCurso.setSelectedIndex(-1);
			                comboBoxNota.setSelectedIndex(-1);
			                comboBoxCampus.setSelectedIndex(-1);
			                grupoTurnos.clearSelection();
			                txtRgmNotas.setText(""); // também limpa o outro campo
			            }
			    });
		btnAtualizarCurso.setIcon(redimensionarIcone("/imagens/setas-circulares.png", 32, 32));
		btnAtualizarCurso.setBounds(136, 236, 89, 62);
		panelCurso.add(btnAtualizarCurso);

		// Botão Pesquisar Tela Curso
		JButton btnListarCurso = new JButton("");
		btnListarCurso.setIcon(redimensionarIcone("/imagens/lupa.png", 32, 32));
		btnListarCurso.setBounds(242, 236, 89, 62);
		panelCurso.add(btnListarCurso);
		
		btnListarCurso.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String rgm = txtRgmDados.getText();
		    	
		    	if (!rgm.isEmpty()) {
		    		try {
		    			AlunoDAO dao = new AlunoDAO();
		    			Aluno aluno = dao.buscarDadosAlunoDados(rgm);
		    			
		    			if (aluno != null) {
		    				txtNomeDados.setText(aluno.getNome());
		    				txtEnderecoDados.setText(aluno.getEndereco());
		    				comboUFDados.setSelectedItem(aluno.getUF());
		    				txtTeleDados.setText(aluno.getTelefone());
		    				txtMuniDados.setText(aluno.getMunicipio());
		    				txtDataNasc.setText(aluno.getDataNasc());
		    				txtCpfDados.setText(aluno.getCPF());
		    				txtEmailDados.setText(aluno.getEmail());
		    				comboBoxCampus.setSelectedItem(aluno.getCampus());
		    				comboBoxCurso.setSelectedItem(aluno.getCurso());
		    				comboBoxSemestre.setSelectedItem(aluno.getSemestre());
		    				String turno = aluno.getPeriodo(); // ou aluno.getTurno(), dependendo de como você armazena o turno

		    	            // Verifica o valor do turno e marca o JRadioButton correspondente
		    	            if (turno != null) {
		    	                switch (turno) {
		    	                    case "Matutino":
		    	                        rdbtnMatutino.setSelected(true);
		    	                        break;
		    	                    case "Vespertino":
		    	                        rdbtnVespertino.setSelected(true);
		    	                        break;
		    	                    case "Noturno":
		    	                        rdbtnNoturno.setSelected(true);
		    	                        break;
		    	                    default:
		    	                        grupoTurnos.clearSelection(); // Se o turno for desconhecido ou nulo
		    	                }
		    	            } else {
		    	                grupoTurnos.clearSelection(); // Caso não haja turno (nulo)
		    	            }

		    				
		    				
		    			} else {
		                    JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
		                }
		    		} catch (Exception ex) {
		    			JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
		    		}
		    	} else {
		            JOptionPane.showMessageDialog(null, "Informe o RGM.");
		    	}
		    }
		});     


		/*
		 * ========================================================================
		 * 	Botão Sair Tela Curso
		 * ========================================================================
		 */
		JButton btnSairCurso = new JButton("");
		btnSairCurso.setIcon(redimensionarIcone("/imagens/saida.png", 32, 32));
		btnSairCurso.setBounds(449, 236, 89, 62);
		panelCurso.add(btnSairCurso);
		btnSairCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});



		/*
		 * ========================================================================
		 * 	Tela Notas e Faltas
		 * ========================================================================
		 */
		JPanel panelNotasFaltas = new JPanel();
		panelNotasFaltas.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Notas e Faltas", null, panelNotasFaltas, null);
		panelNotasFaltas.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("RGM:");
		lblNewLabel_1_1.setBounds(20, 29, 46, 14);
		panelNotasFaltas.add(lblNewLabel_1_1);
		
		txtRgmNotas = new JFormattedTextField();
		txtRgmNotas.setBounds(78, 25, 150, 22);
		panelNotasFaltas.add(txtRgmNotas);
		
		JFormattedTextField txtNomeNotas = new JFormattedTextField();
		txtNomeNotas.setEditable(false);
		txtNomeNotas.setBounds(257, 25, 281, 22);
		panelNotasFaltas.add(txtNomeNotas);
		
		JFormattedTextField txtCursoNotas = new JFormattedTextField();
		txtCursoNotas.setEditable(false);
		txtCursoNotas.setBounds(75, 79, 463, 22);
		panelNotasFaltas.add(txtCursoNotas);
		
		JFormattedTextField txtFaltasNota = new JFormattedTextField();
		txtFaltasNota.setBounds(425, 190, 109, 22);
		panelNotasFaltas.add(txtFaltasNota);
		
		JLabel lblNewLabel_12 = new JLabel("Disciplina");
		lblNewLabel_12.setBounds(20, 132, 62, 14);
		panelNotasFaltas.add(lblNewLabel_12);
		
		String[] disciplina = {
			    "","Algoritmos", "Banco de Dados", "Cálculo", "Engenharia de Software", "Estrutura de Dados", "Gestão de Equipes",
			    "Inglês III", "Inglês IV", "Linguagem de Programação", "Metodologia Científica", "Programação Orientada a Objetos",
			    "Sistemas Operacionais", 
			};
		
		
		comboBoxDisciplina = new JComboBox<>(disciplina);
		comboBoxDisciplina.setBounds(92, 128, 446, 22);
		panelNotasFaltas.add(comboBoxDisciplina);
		
		JLabel lblNewLabel_13 = new JLabel("Semestre");
		lblNewLabel_13.setBounds(20, 193, 62, 14);
		panelNotasFaltas.add(lblNewLabel_13);
		
		String[] semestre = {
			    "","2020-1", "2020-2", "2021-1", "2021-2", "2022-1", "2022-2", "2023-1", "2023-2", "2024-1", "2024-2",
			    "2025-1", "2025-2"
			};
		
		comboBoxSemestre = new JComboBox<>(semestre);
		comboBoxSemestre.setBounds(92, 189, 109, 22);
		panelNotasFaltas.add(comboBoxSemestre);
		
		JLabel lblNewLabel_14 = new JLabel("Nota");
		lblNewLabel_14.setBounds(228, 193, 34, 14);
		panelNotasFaltas.add(lblNewLabel_14);
		
		String[] nota = {
			    "", "0.00", "0.50", "1.00", "1.50", "2.00", "2.00", "3.00", "3.50", "4.00", "4.50",
			    "5.00", "5.50", "6.00", "6.50", "7.00", "7.50", "8.00", "8.50", "9.00", "9.50", "10.00"
			};

		comboBoxNota = new JComboBox<>(nota);
		comboBoxNota.setBounds(269, 189, 74, 22);
		panelNotasFaltas.add(comboBoxNota);
		
		JLabel lblNewLabel_15 = new JLabel("Faltas");
		lblNewLabel_15.setBounds(388, 193, 39, 14);
		panelNotasFaltas.add(lblNewLabel_15);
		
		/*
		 * ========================================================================
		 * Botão Salvar Tela Notas e Faltas
		 * ========================================================================
		 */
		JButton btnSalvarNotas = new JButton("");
		btnSalvarNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        // Criação do objeto aluno
		        Aluno aluno = new Aluno();
		       

		        // Captura dos dados do aluno
		        aluno.setNome(txtNomeNotas.getText()); // Nome do aluno
		        aluno.setRGM(txtRgmNotas.getText()); // RGM do aluno
		        aluno.setSemestre(comboBoxSemestre.getSelectedItem().toString()); // Semestre
		        aluno.setDisciplina(comboBoxDisciplina.getSelectedItem().toString()); // Disciplina
		        
		        // Captura da nota
		        try {
		            // Verifica se há um item selecionado no comboBox
		            Object selectedItem = comboBoxNota.getSelectedItem();
		            if (selectedItem != null) {
		                // Log para verificar o item selecionado
		                //System.out.println("Item selecionado: " + selectedItem.toString());
		                
		                // Convertendo para double com 2 casas decimais
		                double nota = Double.parseDouble(selectedItem.toString()); // Captura de nota
		                aluno.setNota(nota);  // Atribuindo a nota
		                //System.out.println("Nota capturada: " + nota);
		            } else {
		                throw new Exception("Nenhuma nota foi selecionada.");
		            }
		        } catch (NumberFormatException ex) {
		            aluno.setNota(0.0); // Valor padrão caso haja erro na conversão
		            //System.out.println("Erro ao capturar nota. Valor padrão atribuído.");
		        } catch (Exception ex) {
		            System.out.println("Erro: " + ex.getMessage());
		        }

		        // Captura da falta (provavelmente de um JTextField)
		        try {
		            int falta = Integer.parseInt(txtFaltasNota.getText()); // Falta capturada de um JTextField
		            aluno.setFalta(falta);
		            //System.out.println("Falta capturada: " + falta);
		        } catch (NumberFormatException ex) {
		            aluno.setFalta(0); // Valor padrão se não for um número válido
		            //System.out.println("Erro ao capturar falta. Valor padrão atribuído.");
		        }

		        // Agora, você está certo de que os dados estão sendo capturados corretamente.
		        // Chama o método de salvar no DAO
		        try {
		            AlunoDAO dao = new AlunoDAO();
		            dao.salvarNotas(aluno); // Chama o método de salvar notas

		            JOptionPane.showMessageDialog(null, "Notas e faltas salvas com sucesso!");
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao salvar notas: " + ex.getMessage());
		        }
		    }
		});
		
		
		btnSalvarNotas.setIcon(redimensionarIcone("/imagens/salvararquivo.png", 32, 32));
		btnSalvarNotas.setBounds(30, 236, 89, 62);
		panelNotasFaltas.add(btnSalvarNotas);

		/*
		 * ========================================================================
		 * 	Botão Novo tela Notas e Faltas
		 * ========================================================================
		 */
		JButton btnNovoNotas = new JButton("");
		btnNovoNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		                // Limpar campos da interface
		                txtRgmNotas.setText("");
		                txtNomeNotas.setText("");
		                txtCursoNotas.setText("");
		                txtFaltasNota.setText("");
		                comboBoxDisciplina.setSelectedIndex(-1);
		                comboBoxNota.setSelectedIndex(-1);
		                comboBoxSemestre.setSelectedIndex(-1);
		            }
		    });
		btnNovoNotas.setIcon(redimensionarIcone("/imagens/setas-circulares.png", 32, 32));
		btnNovoNotas.setBounds(139, 236, 89, 62);
		panelNotasFaltas.add(btnNovoNotas);

		/*
		 * ========================================================================
		 * 	Botão Pesquisar/Listar
		 * ========================================================================
		 */
		JButton btnListarNotas = new JButton("");
		btnListarNotas.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String rgm = txtRgmNotas.getText();

		        if (!rgm.isEmpty()) {
		            try {
		                AlunoDAO dao = new AlunoDAO();
		                Aluno aluno = dao.pesquisar(rgm);

		                if (aluno != null) {
		                    txtNomeNotas.setText(aluno.getNome());
		                    comboBoxCurso.setSelectedItem(aluno.getCurso());
		                    txtCursoNotas.setText(aluno.getCurso());
		                    // comboBoxDisciplina.setSelectedItem(aluno.getDisciplina()); // Remova ou comente esta linha
		                    comboBoxSemestre.setSelectedItem(aluno.getSemestre());
		                    comboBoxNota.setSelectedItem(aluno.getNota());
		                    txtFaltasNota.setText(String.valueOf(aluno.getFalta()));

		                    int idCursoAluno = aluno.getIdCurso();
		                    //System.out.println("ID do Curso para buscar disciplinas: " + idCursoAluno); // LOG

		                    List<String> disciplinasDoCurso = dao.carregarDisciplinasPorCurso(idCursoAluno);

		                    comboBoxDisciplina.removeAllItems();
		                    for (String disciplina : disciplinasDoCurso) {
		                        comboBoxDisciplina.addItem(disciplina);
		                    }

		                } else {
		                    JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
		                    comboBoxDisciplina.removeAllItems();
		                }
		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
		                ex.printStackTrace();
		                comboBoxDisciplina.removeAllItems();
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Informe o RGM.");
		            comboBoxDisciplina.removeAllItems();
		        }
		    }
		});   
		    	
		btnListarNotas.setIcon(redimensionarIcone("/imagens/lupa.png", 32, 32));
		btnListarNotas.setBounds(245, 236, 89, 62);
		panelNotasFaltas.add(btnListarNotas);

		
		

		/*
		 * ========================================================================
		 * 	Botão Sair Tela Notas e Faltas
		 * ========================================================================
		 */
		JButton btnSairNotas = new JButton("");
		btnSairNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSairNotas.setIcon(redimensionarIcone("/imagens/saida.png", 32, 32));
		btnSairNotas.setBounds(452, 236, 89, 62);
		panelNotasFaltas.add(btnSairNotas);
		
	
		/*
		 * ========================================================================
		 * 	Tela Boletim
		 * ========================================================================
		 */
		JPanel panelBoletim = new JPanel();
		
		tabbedPane.addTab("Boletim", null, panelBoletim, null);
		panelBoletim.setLayout(null);
		
		JLabel lblNewLabel_16 = new JLabel("Nome");
		lblNewLabel_16.setBounds(20, 2, 35, 14);
		panelBoletim.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("RGM");
		lblNewLabel_17.setBounds(351, 2, 46, 14);
		panelBoletim.add(lblNewLabel_17);
		
		JLabel lblNewLabel_18 = new JLabel("Semestre");
		lblNewLabel_18.setBounds(20, 47, 57, 14);
		panelBoletim.add(lblNewLabel_18);
		
		JLabel lblNewLabel_19 = new JLabel("Curso");
		lblNewLabel_19.setBounds(129, 47, 35, 14);
		panelBoletim.add(lblNewLabel_19);
		
		table_Boletim = new JTable();
		table_Boletim.setBounds(10, 101, 552, 226);
		panelBoletim.add(table_Boletim);
		
		// Criando o modelo ( colunas ) da tabela
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Disciplina");
		modelo.addColumn("Nota");
		modelo.addColumn("Faltas");
		
		// Atribuindo o modelo a tabela
		table_Boletim.setModel(modelo);
		JButton btnPdfBoletim = new JButton("");
		/*btnPdfBoletim.setBounds(467, 63, 95, 23);
		panelBoletim.add(btnPdfBoletim);*/
		
		btnPdfBoletim.setIcon(redimensionarIcone("/imagens/arquivopdf.png", 32, 32));
		btnPdfBoletim.setBounds(510, 52, 41, 39);
		panelBoletim.add(btnPdfBoletim);
		
		JButton btnPesquisarBoletim = new JButton("Pesquisar");
		btnPesquisarBoletim.setBounds(379, 62, 116, 23);
		panelBoletim.add(btnPesquisarBoletim);
		
		JFormattedTextField txtNomeBoletim = new JFormattedTextField();
		txtNomeBoletim.setEditable(false);
		txtNomeBoletim.setBounds(20, 21, 319, 22);
		panelBoletim.add(txtNomeBoletim);
		
		JFormattedTextField txtSemestreBoletim = new JFormattedTextField();
		txtSemestreBoletim.setEditable(false);
		txtSemestreBoletim.setBounds(20, 63, 95, 22);
		panelBoletim.add(txtSemestreBoletim);
		
		JFormattedTextField txtCursoBoletim = new JFormattedTextField();
		txtCursoBoletim.setEditable(false);
		txtCursoBoletim.setBounds(125, 63, 214, 22);
		panelBoletim.add(txtCursoBoletim);
		
		JFormattedTextField txtRgmBoletim = new JFormattedTextField();
		txtRgmBoletim.setBounds(351, 20, 211, 23);
		panelBoletim.add(txtRgmBoletim);
		
		/*
		 * ========================================================================
		 * 	Botão para pesquisar Boletim de Aluno
		 * ========================================================================
		 */

		btnPesquisarBoletim.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Garante que a tabela tenha o modelo com os cabeçalhos corretos
		        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Disciplina", "Nota", "Falta"}, 0);
		        table_Boletim.setModel(modelo);

		        try {
		            modelo.setRowCount(0); // Limpa as linhas mas mantém os títulos (agora já definidos)

		            String rgm = txtRgmBoletim.getText();

		            AlunoDAO dao = new AlunoDAO();

		            // 1. Preencher tabela
		            dao.pesquisarBoletimDoAluno(rgm, table_Boletim);

		            // 2. Buscar dados do aluno
		            Aluno aluno = dao.buscarDadosAluno(rgm);
		            if (aluno != null) {
		                txtNomeBoletim.setText(aluno.getNome());
		                txtCursoBoletim.setText(aluno.getCurso());
		                txtSemestreBoletim.setText(aluno.getSemestre());
		            } else {
		                JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
		            }

		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao buscar dados: " + ex.getMessage());
		            ex.printStackTrace();
		        }
		    }
		});
		
		/*
		 * ========================================================================
		 * 	Botão para Gerar PDF
		 * ========================================================================
		 */
		
		btnPdfBoletim.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            // Obtendo os dados do aluno
		            String nomeAluno = txtNomeBoletim.getText().trim();
		            String rgmAluno = txtRgmBoletim.getText().trim();

		            if (nomeAluno.isEmpty() || rgmAluno.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Preencha o nome e o RGM antes de gerar o PDF.");
		                return;
		            }

		            // Monta o nome do arquivo com nome e RGM
		            String nomeFormatado = nomeAluno.replace(" ", "_").replaceAll("[^a-zA-Z0-9_]", "");
		            String caminhoPDF = "C:\\Users\\jorge\\Downloads\\Boletim " + nomeFormatado + "_(" + rgmAluno + ").pdf";

		            // Chama o método para gerar o PDF
		            ExportadorPDF.exportarJTableParaPDF(table_Boletim, caminhoPDF, nomeAluno, rgmAluno);

		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao gerar PDF: " + ex.getMessage());
		            ex.printStackTrace();
		        }
		    }
		});
		
		
		/*
		 * ========================================================================
		 * Método Salvar (Menu) - Esta é a barra de Menu "Aluno"
		 * ========================================================================
		 */
		JMenuItem mntmNewMenuItem = new JMenuItem("Salvar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			        	
			        	try {
				            Aluno aluno = new Aluno();
				            aluno.setNome(txtNomeDados.getText());
				            aluno.setRGM(txtRgmDados.getText());
				            aluno.setDataNasc(txtDataNasc.getText());
				            aluno.setCPF(txtCpfDados.getText());
				            aluno.setEmail(txtEmailDados.getText());
				            aluno.setEndereco(txtEnderecoDados.getText());
				            aluno.setMunicipio(txtMuniDados.getText());
				            aluno.setUF(comboUFDados.getSelectedItem().toString());
				            aluno.setTelefone(txtTeleDados.getText());
				            aluno.setCurso(comboBoxCurso.getSelectedItem().toString());
				            aluno.setCampus(comboBoxCampus.getSelectedItem().toString());
				            aluno.setSemestre("2020-1");  // Semestre padrão

				            // Verifica o turno selecionado
				            String turnoSelecionado = "";
				            if (rdbtnMatutino.isSelected()) {
				                turnoSelecionado = "Matutino";
				            } else if (rdbtnVespertino.isSelected()) {
				                turnoSelecionado = "Vespertino";
				            } else if (rdbtnNoturno.isSelected()) {
				                turnoSelecionado = "Noturno";
				            } else {
				                throw new Exception("Por favor, selecione um turno.");
				            }
							 if (txtNomeDados.getText().equals("") ||txtRgmDados.getText().equals("") || txtCpfDados.getText().equals("   .   .   -  ")|| txtDataNasc.getText().equals("")||txtEmailDados.getText().equals("")||
						        		txtTeleDados.getText().equals("")||txtEnderecoDados.getText().equals("") ||
						        		txtMuniDados.getText().equals("")||comboUFDados.getSelectedItem().equals("UF")||
						        		comboBoxCurso.getSelectedItem().equals("")||
						        		comboBoxCampus.getSelectedItem().equals("")|| rdbtnMatutino.isSelected() == false && rdbtnVespertino.isSelected() == false && rdbtnNoturno.isSelected() == false) {
						        	JOptionPane.showMessageDialog(null, "Nada pode ser nulo");
						        	
						        } else {

				            aluno.setPeriodo(turnoSelecionado);

				            // Salvar no banco
				            AlunoDAO dao = new AlunoDAO();
				            dao.salvar(aluno);

				            JOptionPane.showMessageDialog(null, "Aluno salvo com sucesso!");
						        }
				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, "Erro ao salvar aluno: " + ex.getMessage());
				        }
			        	
			        }
				
		});
		
		mntmNewMenuItem.setHorizontalTextPosition(SwingConstants.LEFT);
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mntmNewMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		mnNewMenu.add(mntmNewMenuItem);
		
		/*
		 * ========================================================================
		 * 	Método Novo (Menu)
		 * ========================================================================
		 */
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Novo");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                // Limpar campos da interface
                txtRgmDados.setText("");
                txtNomeDados.setText("");
                txtCpfDados.setText("");
                txtEmailDados.setText("");
                txtEnderecoDados.setText("");
                txtMuniDados.setText("");
                comboUFDados.setSelectedIndex(-1);
                txtTeleDados.setText("");
                txtDataNasc.setText("");
                comboBoxCurso.setSelectedIndex(-1);
                comboBoxNota.setSelectedIndex(-1);
                comboBoxCampus.setSelectedIndex(-1);
                grupoTurnos.clearSelection();
                txtRgmNotas.setText(""); // também limpa o outro campo
            }
    });
		
		mntmNewMenuItem_1.setHorizontalTextPosition(SwingConstants.LEFT);
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mntmNewMenuItem_1.setHorizontalAlignment(SwingConstants.LEFT);
		mnNewMenu.add(mntmNewMenuItem_1);
		
		/*
		 * ========================================================================
		 * 	Consultar/Pesquisar/Listar (Menu)
		 * ========================================================================
		 */
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Consultar");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	String rgm = txtRgmDados.getText();
		    	
		    	if (!rgm.isEmpty()) {
		    		try {
		    			AlunoDAO dao = new AlunoDAO();
		    			Aluno aluno = dao.buscarDadosAlunoDados(rgm);
		    			
		    			if (aluno != null) {
		    				txtNomeDados.setText(aluno.getNome());
		    				txtEnderecoDados.setText(aluno.getEndereco());
		    				comboUFDados.setSelectedItem(aluno.getUF());
		    				txtTeleDados.setText(aluno.getTelefone());
		    				txtMuniDados.setText(aluno.getMunicipio());
		    				txtDataNasc.setText(aluno.getDataNasc());
		    				txtCpfDados.setText(aluno.getCPF());
		    				txtEmailDados.setText(aluno.getEmail());
		    				comboBoxCampus.setSelectedItem(aluno.getCampus());
		    				comboBoxCurso.setSelectedItem(aluno.getCurso());
		    				comboBoxSemestre.setSelectedItem(aluno.getSemestre());
		    				String turno = aluno.getPeriodo(); // ou aluno.getTurno(), dependendo de como você armazena o turno

		    	            // Verifica o valor do turno e marca o JRadioButton correspondente
		    	            if (turno != null) {
		    	                switch (turno) {
		    	                    case "Matutino":
		    	                        rdbtnMatutino.setSelected(true);
		    	                        break;
		    	                    case "Vespertino":
		    	                        rdbtnVespertino.setSelected(true);
		    	                        break;
		    	                    case "Noturno":
		    	                        rdbtnNoturno.setSelected(true);
		    	                        break;
		    	                    default:
		    	                        grupoTurnos.clearSelection(); // Se o turno for desconhecido ou nulo
		    	                }
		    	            } else {
		    	                grupoTurnos.clearSelection(); // Caso não haja turno (nulo)
		    	            }

		    				
		    				
		    			} else {
		                    JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
		                }
		    		} catch (Exception ex) {
		    			JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
		    		}
		    	} else {
		            JOptionPane.showMessageDialog(null, "Informe o RGM.");
		    	}
		    }
		});
		
		mntmNewMenuItem_2.setHorizontalAlignment(SwingConstants.LEFT);
		mntmNewMenuItem_2.setHorizontalTextPosition(SwingConstants.LEFT);
		mntmNewMenuItem_2.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnNewMenu.add(mntmNewMenuItem_2);
		
		/*
		 * ========================================================================
		 * 	Excluir Disciplina (Menu)
		 * ========================================================================
		 */
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Excluir");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
		        int confirmacao = JOptionPane.showConfirmDialog(null,
		            "Tem certeza que deseja excluir a disciplina do aluno?", "Confirmação",
		            JOptionPane.YES_NO_OPTION);

		        if (confirmacao == JOptionPane.YES_OPTION) {
		            // Tenta obter o RGM de txtRgmDados, se estiver vazio, tenta txtRgmNotas
		            String rgm = txtRgmDados.getText();
		            if (rgm == null || rgm.trim().isEmpty()) {
		                rgm = txtRgmNotas.getText();
		            }

		            // Se ainda assim estiver vazio, mostra aviso
		            if (rgm == null || rgm.trim().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Informe o RGM do aluno.");
		                return;
		            }

		            // Tenta obter o nome da disciplina da comboBox (ou outro campo de texto, se preferir)
		            String nomeDisciplina = (String) comboBoxDisciplina.getSelectedItem();

		            // Se não tiver escolhido uma disciplina, exibe uma mensagem de erro
		            if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Selecione a disciplina a ser excluída.");
		                return;
		            }

		            try {
		                // Criando uma instância do AlunoDAO para acessar o banco de dados
		                AlunoDAO dao = new AlunoDAO();

		                // Chamando o método para excluir a disciplina
		                boolean sucesso = dao.excluirDisciplina(rgm, nomeDisciplina);

		                if (sucesso) {
		                    JOptionPane.showMessageDialog(null, "Disciplina excluída com sucesso!");
		                } else {
		                    JOptionPane.showMessageDialog(null, "Erro ao excluir disciplina ou aluno não encontrado.");
		                }

		                // Limpar campos da interface após a exclusão
		                comboBoxDisciplina.setSelectedIndex(-1);
		                comboBoxNota.setSelectedIndex(-1);
		                comboBoxSemestre.setSelectedIndex(-1);
		                txtNomeBoletim.setText("");
		                txtRgmBoletim.setText("");
		                txtSemestreBoletim.setText("");
		                txtCursoBoletim.setText("");
		                modelo.setRowCount(0);
		                

		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(null, "Erro ao excluir disciplina: " + ex.getMessage());
		            }
		        }
		    }
		});
		
		mntmNewMenuItem_3.setHorizontalAlignment(SwingConstants.LEFT);
		mntmNewMenuItem_3.setHorizontalTextPosition(SwingConstants.LEFT);
		mntmNewMenuItem_3.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnNewMenu.add(mntmNewMenuItem_3);
		
		/*
		 * ========================================================================
		 * 	Sair (Menu)
		 * ========================================================================
		 */
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Sair");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		mntmNewMenuItem_4.setHorizontalAlignment(SwingConstants.LEFT);
		mntmNewMenuItem_4.setHorizontalTextPosition(SwingConstants.LEFT);
		mntmNewMenuItem_4.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mntmNewMenuItem_4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK, false));
		mnNewMenu.add(mntmNewMenuItem_4);
		
		/*
		 * ========================================================================
		 * 	Salvar Notas e Faltas (Menu)
		 * ========================================================================
		 */
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Salvar");
		mntmNewMenuItem_5.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnNewMenu_1.add(mntmNewMenuItem_5);
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        // Criação do objeto aluno
		        Aluno aluno = new Aluno();
		       

		        // Captura dos dados do aluno
		        aluno.setNome(txtNomeNotas.getText()); // Nome do aluno
		        aluno.setRGM(txtRgmNotas.getText()); // RGM do aluno
		        aluno.setSemestre(comboBoxSemestre.getSelectedItem().toString()); // Semestre
		        aluno.setDisciplina(comboBoxDisciplina.getSelectedItem().toString()); // Disciplina
		        
		        // Captura da nota
		        try {
		            // Verifica se há um item selecionado no comboBox
		            Object selectedItem = comboBoxNota.getSelectedItem();
		            if (selectedItem != null) {
		                // Log para verificar o item selecionado
		                //System.out.println("Item selecionado: " + selectedItem.toString());
		                
		                // Convertendo para double com 2 casas decimais
		                double nota = Double.parseDouble(selectedItem.toString()); // Captura de nota
		                aluno.setNota(nota);  // Atribuindo a nota
		                //System.out.println("Nota capturada: " + nota);
		            } else {
		                throw new Exception("Nenhuma nota foi selecionada.");
		            }
		        } catch (NumberFormatException ex) {
		            aluno.setNota(0.0); // Valor padrão caso haja erro na conversão
		            //System.out.println("Erro ao capturar nota. Valor padrão atribuído.");
		        } catch (Exception ex) {
		            System.out.println("Erro: " + ex.getMessage());
		        }

		        // Captura da falta (provavelmente de um JTextField)
		        try {
		            int falta = Integer.parseInt(txtFaltasNota.getText()); // Falta capturada de um JTextField
		            aluno.setFalta(falta);
		            //System.out.println("Falta capturada: " + falta);
		        } catch (NumberFormatException ex) {
		            aluno.setFalta(0); // Valor padrão se não for um número válido
		            //System.out.println("Erro ao capturar falta. Valor padrão atribuído.");
		        }

		        // Agora, você está certo de que os dados estão sendo capturados corretamente.
		        // Chama o método de salvar no DAO
		        try {
		            AlunoDAO dao = new AlunoDAO();
		            dao.salvarNotas(aluno); // Chama o método de salvar notas

		            JOptionPane.showMessageDialog(null, "Notas e faltas salvas com sucesso!");
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Erro ao salvar notas: " + ex.getMessage());
		        }
		    }
		});   
		
		/*
		 * ========================================================================
		 * 	Novo Notas e Faltas (Menu)
		 * ========================================================================
		 */
		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Novo");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                // Limpar campos da interface
                txtRgmNotas.setText("");
                txtNomeNotas.setText("");
                txtCursoNotas.setText("");
                txtFaltasNota.setText("");
                comboBoxDisciplina.setSelectedIndex(-1);
                comboBoxNota.setSelectedIndex(-1);
                comboBoxSemestre.setSelectedIndex(-1);
            }
    });
		
		mntmNewMenuItem_6.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mntmNewMenuItem_6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		mnNewMenu_1.add(mntmNewMenuItem_6);
		
		/*
		 * ========================================================================
		 * 	Excluir Notas e Faltas (Menu)
		 * ========================================================================
		 */
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Excluir");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
			        int confirmacao = JOptionPane.showConfirmDialog(null,
			            "Tem certeza que deseja excluir a disciplina do aluno?", "Confirmação",
			            JOptionPane.YES_NO_OPTION);

			        if (confirmacao == JOptionPane.YES_OPTION) {
			            // Tenta obter o RGM de txtRgmDados, se estiver vazio, tenta txtRgmNotas
			            String rgm = txtRgmDados.getText();
			            if (rgm == null || rgm.trim().isEmpty()) {
			                rgm = txtRgmNotas.getText();
			            }

			            // Se ainda assim estiver vazio, mostra aviso
			            if (rgm == null || rgm.trim().isEmpty()) {
			                JOptionPane.showMessageDialog(null, "Informe o RGM do aluno.");
			                return;
			            }

			            // Tenta obter o nome da disciplina da comboBox (ou outro campo de texto, se preferir)
			            String nomeDisciplina = (String) comboBoxDisciplina.getSelectedItem();

			            // Se não tiver escolhido uma disciplina, exibe uma mensagem de erro
			            if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
			                JOptionPane.showMessageDialog(null, "Selecione a disciplina a ser excluída.");
			                return;
			            }

			            try {
			                // Criando uma instância do AlunoDAO para acessar o banco de dados
			                AlunoDAO dao = new AlunoDAO();

			                // Chamando o método para excluir a disciplina
			                boolean sucesso = dao.excluirDisciplina(rgm, nomeDisciplina);

			                if (sucesso) {
			                    JOptionPane.showMessageDialog(null, "Disciplina excluída com sucesso!");
			                } else {
			                    JOptionPane.showMessageDialog(null, "Erro ao excluir disciplina ou aluno não encontrado.");
			                }

			                // Limpar campos da interface após a exclusão
			                comboBoxDisciplina.setSelectedIndex(-1);
			                comboBoxNota.setSelectedIndex(-1);
			                comboBoxSemestre.setSelectedIndex(-1);
			                txtNomeBoletim.setText("");
			                txtRgmBoletim.setText("");
			                txtSemestreBoletim.setText("");
			                txtCursoBoletim.setText("");
			                modelo.setRowCount(0);
			                

			            } catch (Exception ex) {
			                JOptionPane.showMessageDialog(null, "Erro ao excluir disciplina: " + ex.getMessage());
			            }
			        }
			    }
			});
		
		/*
		 * ========================================================================
		 * 	Consultar Notas e Faltas (Menu)
		 * ========================================================================
		 */
		mntmNewMenuItem_7.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnNewMenu_1.add(mntmNewMenuItem_7);
		
		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Consultar");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String rgm = txtRgmNotas.getText();

		        if (!rgm.isEmpty()) {
		            try {
		                AlunoDAO dao = new AlunoDAO();
		                Aluno aluno = dao.pesquisar(rgm);

		                if (aluno != null) {
		                    txtNomeNotas.setText(aluno.getNome());
		                    comboBoxCurso.setSelectedItem(aluno.getCurso());
		                    txtCursoNotas.setText(aluno.getCurso());
		                    // comboBoxDisciplina.setSelectedItem(aluno.getDisciplina()); // Remova ou comente esta linha
		                    comboBoxSemestre.setSelectedItem(aluno.getSemestre());
		                    comboBoxNota.setSelectedItem(aluno.getNota());
		                    txtFaltasNota.setText(String.valueOf(aluno.getFalta()));

		                    int idCursoAluno = aluno.getIdCurso();
		                    //System.out.println("ID do Curso para buscar disciplinas: " + idCursoAluno); // LOG

		                    List<String> disciplinasDoCurso = dao.carregarDisciplinasPorCurso(idCursoAluno);

		                    comboBoxDisciplina.removeAllItems();
		                    for (String disciplina : disciplinasDoCurso) {
		                        comboBoxDisciplina.addItem(disciplina);
		                    }

		                } else {
		                    JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
		                    comboBoxDisciplina.removeAllItems();
		                }
		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
		                ex.printStackTrace();
		                comboBoxDisciplina.removeAllItems();
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Informe o RGM.");
		            comboBoxDisciplina.removeAllItems();
		        }
		    }
		});  
		
		mntmNewMenuItem_8.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnNewMenu_1.add(mntmNewMenuItem_8);
		
		/*
		 * ========================================================================
		 * 	Botão Excluir Curso
		 * ========================================================================
		 */
		
		JButton btnExcluirCurso = new JButton("");
		btnExcluirCurso.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int confirmacao = JOptionPane.showConfirmDialog(null,
		            "Tem certeza que deseja excluir este aluno?", "Confirmação",
		            JOptionPane.YES_NO_OPTION);

		        if (confirmacao == JOptionPane.YES_OPTION) {
		            // Tenta obter o RGM de txtRgmDados, se estiver vazio, tenta txtRgmNotas
		            String rgm = txtRgmDados.getText();
		            if (rgm == null || rgm.trim().isEmpty()) {
		                rgm = txtRgmNotas.getText();
		            }

		            // Se ainda assim estiver vazio, mostra aviso
		            if (rgm == null || rgm.trim().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Informe o RGM do aluno a ser excluído.");
		                return;
		            }

		            try {
		                AlunoDAO dao = new AlunoDAO();
		                dao.excluirAluno(rgm);

		                JOptionPane.showMessageDialog(null, "Aluno excluído com sucesso!");

		                // Limpar campos da interface após exclusão
		                txtRgmDados.setText("");
		                txtNomeDados.setText("");
		                txtCpfDados.setText("");
		                txtEmailDados.setText("");
		                txtEnderecoDados.setText("");
		                txtMuniDados.setText("");
		                comboUFDados.setSelectedIndex(-1);
		                txtTeleDados.setText("");
		                txtDataNasc.setText("");
		                txtNomeNotas.setText("");
		                txtCursoNotas.setText("");
		                comboBoxCurso.setSelectedIndex(-1);
		                comboBoxCampus.setSelectedIndex(-1);
		                comboBoxDisciplina.setSelectedIndex(-1);
		                comboBoxSemestre.setSelectedIndex(-1);
		                comboBoxNota.setSelectedIndex(-1);
		                grupoTurnos.clearSelection();
		                txtRgmNotas.setText(""); // também limpa o outro campo

		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(null, "Erro ao excluir aluno: " + ex.getMessage());
		            }
		        }
		    }
		});
		btnExcluirCurso.setIcon(redimensionarIcone("/imagens/lixo.png", 32, 32));
		btnExcluirCurso.setBounds(346, 236, 89, 62);
		panelCurso.add(btnExcluirCurso);
		/*
		 * ======================================
		 * Botão excluir tela Notas e Faltas
		 * ======================================
		 */
		JButton btnExcluirNotas = new JButton("");
		btnExcluirNotas.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int confirmacao = JOptionPane.showConfirmDialog(null,
		            "Tem certeza que deseja excluir a disciplina do aluno?", "Confirmação",
		            JOptionPane.YES_NO_OPTION);

		        if (confirmacao == JOptionPane.YES_OPTION) {
		            // Tenta obter o RGM de txtRgmDados, se estiver vazio, tenta txtRgmNotas
		            String rgm = txtRgmDados.getText();
		            if (rgm == null || rgm.trim().isEmpty()) {
		                rgm = txtRgmNotas.getText();
		            }

		            // Se ainda assim estiver vazio, mostra aviso
		            if (rgm == null || rgm.trim().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Informe o RGM do aluno.");
		                return;
		            }

		            // Tenta obter o nome da disciplina da comboBox (ou outro campo de texto, se preferir)
		            String nomeDisciplina = (String) comboBoxDisciplina.getSelectedItem();

		            // Se não tiver escolhido uma disciplina, exibe uma mensagem de erro
		            if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Selecione a disciplina a ser excluída.");
		                return;
		            }

		            try {
		                // Criando uma instância do AlunoDAO para acessar o banco de dados
		                AlunoDAO dao = new AlunoDAO();

		                // Chamando o método para excluir a disciplina
		                boolean sucesso = dao.excluirDisciplina(rgm, nomeDisciplina);

		                if (sucesso) {
		                    JOptionPane.showMessageDialog(null, "Disciplina excluída com sucesso!");
		                } else {
		                    JOptionPane.showMessageDialog(null, "Erro ao excluir disciplina ou aluno não encontrado.");
		                }

		                // Limpar campos da interface após a exclusão
		                comboBoxDisciplina.setSelectedIndex(-1);
		                comboBoxNota.setSelectedIndex(-1);
		                comboBoxSemestre.setSelectedIndex(-1);
		                txtNomeBoletim.setText("");
		                txtRgmBoletim.setText("");
		                txtSemestreBoletim.setText("");
		                txtCursoBoletim.setText("");
		                modelo.setRowCount(0);
		                

		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(null, "Erro ao excluir disciplina: " + ex.getMessage());
		            }
		        }
		    }
		});
		btnExcluirNotas.setIcon(redimensionarIcone("/imagens/lixo.png", 32, 32));
		btnExcluirNotas.setBounds(349, 236, 89, 62);
		panelNotasFaltas.add(btnExcluirNotas);
		
	}
		
}