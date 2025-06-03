# AEDs3
## TP1 - Relacionamento 1:N
### O que o trabalho faz?
- Este programa permite que o usuário crie e gerencie um arquivo contendo várias séries, cada uma com diversos episódios associados. É possível realizar operações como inclusão, busca, alteração e exclusão dessas entidades. O sistema utiliza arquivos indexados, com suporte a Hash Extensível e Árvore B+, garantindo desempenho nas operações de acesso e relacionamento 1:N entre séries e episódios.

### Nome dos Participantes
- Matheus Henrique Gonçalves

- Henrique Saldanha Mendes Veloso

- Gabriel Reis Villela

### Experiência com o trabalho
- O desenvolvimento deste projeto foi desafiador, especialmente por ser a nossa primeira experiência com arquivos indexados. No entanto, foi extremamente gratificante ver tudo funcionando corretamente ao final. Aprendemos bastante sobre estruturação de dados em arquivos, uso de índices e boas práticas de organização em sistemas CRUD complexos.

### Checklist
- As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente? Sim

- As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente? Sim

- Essas operações usam a classe CRUD genérica e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos? Sim

- O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios? Sim

- Há uma Árvore B+ que registra o relacionamento 1:N entre episódios e séries? Sim

- Há uma visualização das séries que exibe os episódios por temporada? Sim

- A remoção de séries verifica se há episódios vinculados a ela? Sim

- A inclusão de episódios só é permitida para séries existentes? Sim

- O trabalho está funcionando corretamente? Sim

- O trabalho está completo? Sim

- O trabalho é original e não é cópia de outro grupo? Sim

### Pendências
- Permitir a inclusão de episódios com o mesmo nome, desde que pertençam a séries diferentes

- Corrigir problema ao lidar com sinopses muito grandes, que podem causar erro no armazenamento
