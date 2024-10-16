Insetos em Ordem
================
Este projeto foi desenvolvido no âmbito da Unidade Curricular de Projeto
Integrado da Licenciatura de Engenharia Informática da Escola Superior de
Tecnologia e Gestão do Instituto Politécnico de Beja.

Neste projeto foi implementada uma aplicação Android com o objectivo de tornar
acessível ao público geral o uso de chaves dicotómicas. A aplicação foi
desenvolvida de forma a poder ser adaptada a outras chaves.

Neste projeto foram incluídos conteúdos para uma chave de identificação de
insetos até ao nível da respectiva ordem.


Requisitos
----------
Este projeto foi desenvolvido em Android Studio 3.0.1 com SDK versão 26 para
API level 26, mas poderá funcionar para outras versões.

O projeto tem ainda como dependências as bibliotecas Room 1.0.0 e PhotoView
2.1.3.


Como Compilar
-------------
- `git clone git@gitlab.com:AppDicotomica/AppDicotomica.git`
- Abrir o projeto com o Android Studio
- Ao compilar o projeto as dependências necessárias serão descarregadas pelo
gradle


Como Extender
-------------
A definição da chave dicotómica encontra-se no ficheiro XML em
`app/src/main/assets/chave.xml` as imagens podem ser colocadas na mesma pasta,
sendo que os respectivos caminhos referidos no xml serão procurados pela
aplicação relativamente a esta pasta.

Durante a edição da chave, os caminhos relativos dos assets podem
ser obtidos facilmente com o menu de contexto dos mesmos na opção
`Copy Relative Path Ctrl+Alt+Shift+C`.

A chave segue um formato simples em que cada nó deve estar identificado
por um código id unívoco.


Créditos
--------
- Programadores: Vasco Flores, José Rodrigues alunos do Instituto Poltécnico de Beja
- Professor Tutor: João Paulo Barros
- Conteúdos do livro Insetos em Ordem, P. Garcia Pereira, E. Monteiro, F. Vala, e C. Luís
- Aplicação desenvolvida em colaboração com a Dra. Patrícia Garcia Pereira e
pela equipa da Plataforma de Ciência Aberta
