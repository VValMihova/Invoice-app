function changeLanguage() {
    const languageSelect = document.getElementById('languageSelect');
    const selectedLanguage = languageSelect.value;
    window.location.href = '?lang=' + selectedLanguage;
}
