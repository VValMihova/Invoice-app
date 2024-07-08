// invoice.js

let itemIndex = 1;

function addInvoiceItem() {
    const container = document.getElementById('invoiceItemsContainer');
    const newRow = document.createElement('tr');
    newRow.innerHTML = `
        <td><input type="text" name="items[${itemIndex}].name" placeholder="Item Name" class="form-control" required /></td>
        <td><input type="number" name="items[${itemIndex}].quantity" placeholder="Quantity" class="form-control" oninput="calculateTotalPrice(this)" required /></td>
        <td><input type="number" name="items[${itemIndex}].unitPrice" placeholder="Unit Price" class="form-control" oninput="calculateTotalPrice(this)" required /></td>
        <td><input type="number" name="items[${itemIndex}].totalPrice" placeholder="Total Price" class="form-control" readonly /></td>
        <td><button type="button" class="btn btn-danger" onclick="removeInvoiceItem(this)">Remove</button></td>
    `;
    container.appendChild(newRow);
    itemIndex++;
}

function removeInvoiceItem(button) {
    const row = button.closest('tr');
    row.remove();
    calculateTotalAmounts();
}

function calculateTotalPrice(element) {
    const row = element.closest('tr');
    const quantity = row.querySelector('input[name*="quantity"]').value;
    const unitPrice = row.querySelector('input[name*="unitPrice"]').value;

    function calculateTotalPrice(element) {
        const row = element.closest('tr');
        const quantity = row.querySelector('input[name*="quantity"]').value;
        const unitPrice = row.querySelector('input[name*="unitPrice"]').value;
        const totalPrice = row.querySelector('input[name*="totalPrice"]');

        if (quantity && unitPrice) {
            totalPrice.value = (quantity * unitPrice).toFixed(2);
        } else {
            totalPrice.value = '';
        }

        calculateTotalAmounts();
    }

    function calculateTotalAmounts() {
        const totalAmountField = document.getElementById('totalAmount');
        const vatField = document.getElementById('vat');
        const amountDueField = document.getElementById('amountDue');

        let totalAmount = 0;

        document.querySelectorAll('input[name*="items"][name*="totalPrice"]').forEach(item => {
            totalAmount += parseFloat(item.value) || 0;
        });

        const vat = totalAmount * 0.2;
        const amountDue = totalAmount + vat;

        totalAmountField.value = totalAmount.toFixed(2);
        vatField.value = vat.toFixed(2);
        amountDueField.value = amountDue.toFixed(2);
    }

    const bankAccounts = /*[[${bankAccounts}]]*/ [];

    function updateBankDetails(select) {
        const selectedId = select.value;
        const selectedBankAccount = bankAccounts.find(account => account.id === selectedId);

        if (selectedBankAccount) {
            document.getElementById('iban').value = selectedBankAccount.iban;
            document.getElementById('bic').value = selectedBankAccount.bic;
            document.getElementById('currency').value = selectedBankAccount.currency;
        } else {
            document.getElementById('iban').value = '';
            document.getElementById('bic').value = '';
            document.getElementById('currency').value = '';
        }
    }
}