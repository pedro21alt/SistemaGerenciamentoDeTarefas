// script.js

// Função auxiliar para exibir mensagens de erro abaixo de um input
function exibirErro(elementoInput, mensagem) {
    // Remover erro existente para este elemento para evitar duplicidade
    const erroExistente = elementoInput.nextElementSibling;
    if (erroExistente && erroExistente.classList.contains('error-message')) {
        erroExistente.remove();
    }

    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = mensagem;
    // Insere a div de erro após o elemento input
    elementoInput.parentNode.insertBefore(errorDiv, elementoInput.nextSibling);
}

// Função auxiliar para remover mensagens de erro
function removerErro(elementoInput) {
    const erroExistente = elementoInput.nextElementSibling;
    if (erroExistente && erroExistente.classList.contains('error-message')) {
        erroExistente.remove();
    }
}

// Validação para a página de login (login.html)
function validarLogin(event) {
    const emailInput = document.getElementById('email');
    const senhaInput = document.getElementById('senha');
    let isValid = true; // Flag para controlar a validade geral do formulário

    // Limpar mensagens de erro anteriores antes de revalidar
    removerErro(emailInput);
    removerErro(senhaInput);

    // Validação de Email
    if (emailInput.value.trim() === '') {
        exibirErro(emailInput, 'O email é obrigatório.');
        isValid = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput.value)) { // Regex para formato de email
        exibirErro(emailInput, 'Formato de email inválido.');
        isValid = false;
    }

    // Validação de Senha
    if (senhaInput.value.trim() === '') {
        exibirErro(senhaInput, 'A senha é obrigatória.');
        isValid = false;
    } else if (senhaInput.value.length < 6) { // Exemplo: senha mínima de 6 caracteres
        exibirErro(senhaInput, 'A senha deve ter no mínimo 6 caracteres.');
        isValid = false;
    }

    // Se isValid for false, impede a submissão do formulário
    if (!isValid) {
        event.preventDefault();
    } else {
        // Se as validações JS passarem, simular um "sucesso" para a atividade
        alert('Login validado com sucesso! (Simulação frontend)');
        event.preventDefault(); // Impede o envio real para o backend para esta atividade
        window.location.href = 'dashboard.html'; // Redireciona para o dashboard após validação de sucesso
    }
    return isValid;
}

// Validação para a página de cadastro de tarefa (nova-tarefa.html)
function validarTarefa(event) {
    const tituloInput = document.getElementById('titulo');
    const dataVencimentoInput = document.getElementById('dataVencimento');
    let isValid = true;

    // Limpar mensagens de erro anteriores
    removerErro(tituloInput);
    removerErro(dataVencimentoInput);

    // Validação de Título
    if (tituloInput.value.trim() === '') {
        exibirErro(tituloInput, 'O título da tarefa é obrigatório.');
        isValid = false;
    }

    // Validação de Data de Vencimento
    if (dataVencimentoInput.value.trim() === '') {
        exibirErro(dataVencimentoInput, 'A data de vencimento é obrigatória.');
        isValid = false;
    } else {
        const hoje = new Date();
        hoje.setHours(0, 0, 0, 0); // Zera a hora para comparar apenas a data
        const dataVencimento = new Date(dataVencimentoInput.value); // Converte a string para objeto Date

        if (dataVencimento < hoje) {
            exibirErro(dataVencimentoInput, 'A data de vencimento não pode ser no passado.');
            isValid = false;
        }
    }

    if (!isValid) {
        event.preventDefault(); // Impede o envio do formulário se houver erros
    } else {
        // Simulação de sucesso
        alert('Tarefa validada e salva! (Simulação frontend)');
        event.preventDefault(); // Impede o envio real
        window.location.href = 'dashboard.html'; // Redireciona de volta para o dashboard
    }
    return isValid;
}

// Validação para a página de cadastro de usuário (cadastro.html)
function validarCadastro(event) {
    const emailInput = document.getElementById('emailCadastro');
    const senhaInput = document.getElementById('senhaCadastro');
    const confirmarSenhaInput = document.getElementById('confirmarSenhaCadastro');
    let isValid = true;

    // Limpar mensagens de erro anteriores
    removerErro(emailInput);
    removerErro(senhaInput);
    removerErro(confirmarSenhaInput);

    // Validação de Email
    if (emailInput.value.trim() === '') {
        exibirErro(emailInput, 'O email é obrigatório.');
        isValid = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailInput.value)) {
        exibirErro(emailInput, 'Formato de email inválido.');
        isValid = false;
    }

    // Validação de Senha
    if (senhaInput.value.trim() === '') {
        exibirErro(senhaInput, 'A senha é obrigatória.');
        isValid = false;
    } else if (senhaInput.value.length < 6) {
        exibirErro(senhaInput, 'A senha deve ter no mínimo 6 caracteres.');
        isValid = false;
    }

    // Validação de Confirmação de Senha
    if (confirmarSenhaInput.value.trim() === '') {
        exibirErro(confirmarSenhaInput, 'Confirme sua senha.');
        isValid = false;
    } else if (senhaInput.value !== confirmarSenhaInput.value) { // Verifica se as senhas são iguais
        exibirErro(confirmarSenhaInput, 'As senhas não coincidem.');
        isValid = false;
    }

    if (!isValid) {
        event.preventDefault(); // Impede o envio do formulário se houver erros
    } else {
        // Simulação de sucesso
        alert('Cadastro validado com sucesso! (Simulação frontend)');
        event.preventDefault();
        window.location.href = 'login.html'; // Redireciona para a página de login após o cadastro
    }
    return isValid;
}
