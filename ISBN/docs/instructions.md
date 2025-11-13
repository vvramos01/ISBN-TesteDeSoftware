# Roteiro de Testes — `validarISBN13(String isbn)` (com exemplo de cálculo)

## Comportamento esperado (o que testar)
- Aceitar ISBN-13 **com hífens e/ou espaços** em qualquer posição (ex.: `978-0-306-40615-7`, `978 0306406157`) e **sem separadores** (`9780306406157`).
- Ignorar **espaços externos** (`" 9780306406157 "` → `true`).
- Após sanitização (remover hífens e espaços), devem restar **13 dígitos** iniciando por **978** ou **979**.
- Validar o **dígito verificador (DV)** pelo algoritmo do ISBN-13.
- Rejeitar entradas **nulas/vazias**, com **caracteres inválidos** (letras, pontos, outros símbolos), **tamanho ≠ 13** após sanitização, **prefixo inválido** e **DV incorreto**.

---

## Roteiro de commits (curto e objetivo)

1) **`test(isbn): caso feliz com e sem separadores`**
    - Casos que devem ser válidos quando implementado:
        - `9780306406157`
        - `978-0-306-40615-7`

2) **`test(isbn): entradas nulas/vazias e espaços externos`**
    - `null` → `false`
    - `""` → `false`
    - `" 9780306406157 "` → `true`

3) **`test(isbn): tamanho após sanitização deve ser 13`**
    - `978030640615` (12 dígitos) → `false`
    - `97803064061570` (14 dígitos) → `false`

4) **`test(isbn): prefixo deve ser 978 ou 979`**
    - `9770306406157` → `false`
    - `1234567890123` → `false`

5) **`test(isbn): caracteres/Separadores inválidos`**
    - `97803A6406157` (letra) → `false`
    - `978.0306406157` (ponto) → `false`

6) **`test(isbn): dígito verificador incorreto`**
    - `9780306406158` (mesmo número com DV trocado) → `false`

7) **`feat(isbn): implementação mínima para passar todos os testes`**
    - Sanitizar (trim; remover hífens e espaços), checar 13 dígitos e prefixo, calcular DV, comparar.

8) **`refactor(isbn): limpar código e documentar regra`**
    - Pequenos ajustes e comentários explicando o algoritmo.

---

## Como validar um ISBN-13 (algoritmo resumido)
1. **Sanitizar**
    - Se `isbn` for `null`/vazio ⇒ **false**.
    - `trim()` e **remover** hífens e espaços internos.
    - Se o restante **não tiver 13 dígitos**, ⇒ **false**.
2. **Prefixo**
    - Deve começar com **978** ou **979**; caso contrário ⇒ **false**.
3. **Dígito verificador (13º dígito)**
    - Considere os **12 primeiros dígitos** `d1..d12`.
    - Calcule a soma alternando pesos **1,3,1,3,…**:  
      `S = d1×1 + d2×3 + d3×1 + d4×3 + ... + d12×3`
    - `DV = (10 − (S % 10)) % 10`. ISBN é válido se `DV == d13`.

---

## Exemplo prático de cálculo (ISBN `9780306406157`)
- Dígitos (sem separadores): `9 7 8 0 3 0 6 4 0 6 1 5 | 7` (os 12 primeiros + DV esperado **7**)
- **Pesos** para os 12 primeiros dígitos: `1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3`

| Posição | Dígito | Peso | Produto |
|:------:|:------:|:----:|:-------:|
| d1     | 9      | 1    | 9       |
| d2     | 7      | 3    | 21      |
| d3     | 8      | 1    | 8       |
| d4     | 0      | 3    | 0       |
| d5     | 3      | 1    | 3       |
| d6     | 0      | 3    | 0       |
| d7     | 6      | 1    | 6       |
| d8     | 4      | 3    | 12      |
| d9     | 0      | 1    | 0       |
| d10    | 6      | 3    | 18      |
| d11    | 1      | 1    | 1       |
| d12    | 5      | 3    | 15      |

- **Soma** `S = 9+21+8+0+3+0+6+12+0+18+1+15 = 93`
- `S % 10 = 3` ⇒ `DV = (10 − 3) % 10 = 7`
- O 13º dígito do ISBN é **7**, então `9780306406157` é **válido**.

> Outro exemplo com prefixo 979: para `9791234567896`, o cálculo resulta em **DV = 6**, portanto é válido.

---

## Tabela de cenários (guia rápido)

| Categoria                     | Entrada                         | Motivo/Observação                        | Esperado |
|------------------------------|----------------------------------|-------------------------------------------|----------|
| Válido (sem separadores)     | `9780306406157`                 | Clássico válido                           | `true`   |
| Válido (com hífens)          | `978-0-306-40615-7`            | Hífens aceitos em qualquer posição        | `true`   |
| Válido (prefixo 979)         | `9791234567896`                | 979 com DV correto                        | `true`   |
| Válido (com espaços internos)| `978 0306406157`               | Espaços internos aceitos                  | `true`   |
| Nulo/Vazio                   | `null` / `""`                  | Sem conteúdo                              | `false`  |
| Espaços externos             | ` 9780306406157 `              | `trim()` deve aceitar                     | `true`   |
| Tamanho < 13                 | `978030640615`                 | 12 dígitos após sanitização               | `false`  |
| Tamanho > 13                 | `97803064061570`               | 14 dígitos após sanitização               | `false`  |
| Prefixo inválido             | `9770306406157`                | Não é 978/979                             | `false`  |
| Caractere inválido           | `97803A6406157`                | Letra no meio                             | `false`  |
| Separador inválido           | `978.0306406157`               | Ponto não é separador permitido           | `false`  |
| DV incorreto                 | `9780306406158`                | DV trocado                                | `false`  |

---

### Dicas para os alunos
- Comece sempre pelos **testes** (um por commit). Só depois implemente o mínimo para passar.
- Commits **pequenos e descritivos** facilitam a revisão e a avaliação.