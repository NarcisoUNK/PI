Insetos em Ordem + Que Rocha é Esta?
====================================
Este projeto foi desenvolvido no âmbito da Unidade Curricular de Projeto Integrado da Licenciatura de Engenharia Informática da Escola Superior de Tecnologia e Gestão do Instituto Politécnico de Beja.

Neste projeto foram implementadas duas aplicações Android com o objetivo de tornar acessível ao público geral o uso de chaves dicotómicas. As aplicações foram desenvolvidas de forma a poderem ser adaptadas a outras chaves.

As aplicações incluem conteúdos para uma chave de identificação de insetos até ao nível da respetiva ordem e uma chave de identificação de rochas.

Requisitos
----------
Este projeto foi desenvolvido em Android Studio com suporte para Kotlin. A versão mínima do SDK é 26 para API level 26, mas poderá funcionar para outras versões.

O projeto tem como dependências as bibliotecas Room 1.0.0 e PhotoView 2.1.3.

Como Compilar
-------------
- `git clone https://github.com/NarcisoUNK/PI.git`
- Abrir o projeto com o Android Studio
- Ao compilar o projeto, as dependências necessárias serão descarregadas pelo gradle

Como Extender
-------------
A definição da chave dicotómica encontra-se no ficheiro XML em `app/src/main/assets/chave.xml`. As imagens podem ser colocadas na mesma pasta, sendo que os respetivos caminhos referidos no XML serão procurados pela aplicação relativamente a esta pasta.

Durante a edição da chave, os caminhos relativos dos assets podem ser obtidos facilmente com o menu de contexto dos mesmos na opção `Copy Relative Path Ctrl+Alt+Shift+C`.

A chave segue um formato simples em que cada nó deve estar identificado por um código id unívoco.

Créditos
--------
- Programadores: Rafael Conceição Narciso, José Mauricio, Vasco Flores, José Rodrigues, alunos do Instituto Politécnico de Beja
- Professores Tutores: João Paulo Barros, Sofia Soares
- Conteúdos do livro Insetos em Ordem, P. Garcia Pereira, E. Monteiro, F. Vala, e C. Luís
- Conteúdos do livro Estudo e Classificação das Rochas por Exame Macroscópio, Joaquim Botelho da Costa
- Aplicação desenvolvida em colaboração com a Dra. Patrícia Garcia Pereira e pela equipa da Plataforma de Ciência Aberta

Os vídeos dos testes de utilizadores, bugs e opções da chave dicotómica podem ser encontrados no repositório do Dropbox [aqui](https://www.dropbox.com/scl/fo/05chv1ufs53m9h4fk2zpf/ALyHEoM3BH4S_bso0ko9RWk?rlkey=87tqx3nh8n1wby56us8h9rrqp&st=zivr7pmn&dl=0).
