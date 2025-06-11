class ExtensibleHashTable {
    constructor(bucketSize = 3) {
        this.globalDepth = 0;
        this.bucketSize = bucketSize;
        this.directory = [0];
        this.buckets = [{ localDepth: 0, elements: [] }];
        this.nextBucketId = 1;
    }

    hash(key, depth) {
        return Math.abs(key) & ((1 << depth) - 1);
    }

    insert(key) {
        const index = this.hash(key, this.globalDepth);
        const bucketIndex = this.directory[index];
        const bucket = this.buckets[bucketIndex];

        // Verifica se já existe
        if (bucket.elements.includes(key)) {
            return { success: false, message: `Elemento ${key} já existe!` };
        }

        // Tenta inserir no cesto
        if (bucket.elements.length < this.bucketSize) {
            bucket.elements.push(key);
            bucket.elements.sort((a, b) => a - b);
            return { success: true, message: `Elemento ${key} inserido com sucesso!` };
        }

        // Dividir cesto se estiver cheio
        return this.splitBucket(key, bucketIndex);
    }

    splitBucket(key, bucketIndex) {
        const bucket = this.buckets[bucketIndex];
        
        // Aumenta o diretorio se necessário
        if (bucket.localDepth === this.globalDepth) {
            this.doubleDirectory();
        }

        // Cria novo cesto
        const newBucketIndex = this.nextBucketId++;
        const newBucket = { localDepth: bucket.localDepth + 1, elements: [] };
        this.buckets[newBucketIndex] = newBucket;

        // Aumenta profundidade local
        bucket.localDepth++;

        const allElements = [...bucket.elements, key];
        bucket.elements = [];

        for (const element of allElements) {
            const newHash = this.hash(element, bucket.localDepth);
            if (newHash & (1 << (bucket.localDepth - 1))) {
                newBucket.elements.push(element);
            } else {
                bucket.elements.push(element);
            }
        }

        bucket.elements.sort((a, b) => a - b);
        newBucket.elements.sort((a, b) => a - b);

        this.updateDirectoryPointers(bucketIndex, newBucketIndex, bucket.localDepth);

        return { success: true, message: `Cesto dividido! Elemento ${key} inserido.` };
    }

    doubleDirectory() {
        this.globalDepth++;
        const oldSize = this.directory.length;
        for (let i = 0; i < oldSize; i++) {
            this.directory.push(this.directory[i]);
        }
    }

    updateDirectoryPointers(oldBucketIndex, newBucketIndex, localDepth) {
        const step = 1 << localDepth;
        for (let i = 0; i < this.directory.length; i += step) {
            const pattern = i & ((1 << (localDepth - 1)) - 1);
            const shouldPointToNew = i & (1 << (localDepth - 1));
            
            if (this.directory[i] === oldBucketIndex) {
                if (shouldPointToNew) {
                    this.directory[i] = newBucketIndex;
                }
            }
        }
    }

    delete(key) {
        const index = this.hash(key, this.globalDepth);
        const bucketIndex = this.directory[index];
        const bucket = this.buckets[bucketIndex];

        const elementIndex = bucket.elements.indexOf(key);
        if (elementIndex === -1) {
            return { success: false, message: `Elemento ${key} não encontrado!` };
        }

        bucket.elements.splice(elementIndex, 1);
        return { success: true, message: `Elemento ${key} removido com sucesso!` };
    }

    search(key) {
        const index = this.hash(key, this.globalDepth);
        const bucketIndex = this.directory[index];
        const bucket = this.buckets[bucketIndex];

        const found = bucket.elements.includes(key);
        return { 
            success: found, 
            message: found ? `Elemento ${key} encontrado no cesto ${bucketIndex}!` : `Elemento ${key} não encontrado!`,
            bucketIndex: found ? bucketIndex : -1
        };
    }

    getTotalElements() {
        return this.buckets.reduce((total, bucket) => total + bucket.elements.length, 0);
    }

    getLoadFactor() {
        const totalCapacity = this.buckets.length * this.bucketSize;
        const totalElements = this.getTotalElements();
        return Math.round((totalElements / totalCapacity) * 100);
    }
}

let hashTable = new ExtensibleHashTable(3);

function renderVisualization() {
    renderDirectory();
    renderBuckets();
    updateStats();
}

function renderDirectory() {
    const container = document.getElementById('directoryEntries');
    const globalDepthSpan = document.getElementById('globalDepth');
    
    globalDepthSpan.textContent = hashTable.globalDepth;
    container.innerHTML = '';

    hashTable.directory.forEach((bucketIndex, index) => {
        const entry = document.createElement('div');
        entry.className = 'directory-entry';
        entry.innerHTML = `
            <div class="directory-index">${index}</div>
            <div class="directory-pointer">→ Cesto ${bucketIndex}</div>
        `;
        container.appendChild(entry);
    });
}

function renderBuckets() {
    const container = document.getElementById('bucketsContainer');
    container.innerHTML = '';

    hashTable.buckets.forEach((bucket, index) => {
        if (!bucket) return;

        const bucketDiv = document.createElement('div');
        bucketDiv.className = 'bucket';
        bucketDiv.id = `bucket-${index}`;

        const elementsHtml = [];
        for (let i = 0; i < hashTable.bucketSize; i++) {
            if (i < bucket.elements.length) {
                elementsHtml.push(`<div class="element">${bucket.elements[i]}</div>`);
            } else {
                elementsHtml.push(`<div class="empty-slot">-</div>`);
            }
        }

        bucketDiv.innerHTML = `
            <div class="bucket-header">
                <div class="bucket-title">Cesto ${index}</div>
                <div class="local-depth">PL: ${bucket.localDepth}</div>
            </div>
            <div class="bucket-elements">
                ${elementsHtml.join('')}
            </div>
        `;

        container.appendChild(bucketDiv);
    });
}

function updateStats() {
    document.getElementById('totalElements').textContent = hashTable.getTotalElements();
    document.getElementById('totalBuckets').textContent = hashTable.buckets.filter(b => b).length;
    document.getElementById('loadFactor').textContent = hashTable.getLoadFactor() + '%';
}

function showMessage(text, type = 'info') {
    const messageDiv = document.getElementById('message');
    messageDiv.textContent = text;
    messageDiv.className = `message ${type}`;
    setTimeout(() => {
        messageDiv.textContent = '';
        messageDiv.className = 'message';
    }, 3000);
}

function highlightBucket(bucketIndex) {
    const bucketElement = document.getElementById(`bucket-${bucketIndex}`);
    if (bucketElement) {
        bucketElement.classList.add('highlight');
        setTimeout(() => {
            bucketElement.classList.remove('highlight');
        }, 2000);
    }
}

function insertElement() {
    const input = document.getElementById('elementInput');
    const value = parseInt(input.value);
    
    if (isNaN(value) || value < 0) {
        showMessage('Por favor, digite um número válido (≥ 0)!', 'error');
        return;
    }

    const result = hashTable.insert(value);
    showMessage(result.message, result.success ? 'success' : 'error');
    
    if (result.success) {
        input.value = '';
        renderVisualization();
    }
}

function deleteElement() {
    const input = document.getElementById('elementInput');
    const value = parseInt(input.value);
    
    if (isNaN(value)) {
        showMessage('Por favor, digite um número válido!', 'error');
        return;
    }

    const result = hashTable.delete(value);
    showMessage(result.message, result.success ? 'success' : 'error');
    
    if (result.success) {
        input.value = '';
        renderVisualization();
    }
}

function searchElement() {
    const input = document.getElementById('elementInput');
    const value = parseInt(input.value);
    
    if (isNaN(value)) {
        showMessage('Por favor, digite um número válido!', 'error');
        return;
    }

    const result = hashTable.search(value);
    showMessage(result.message, result.success ? 'success' : 'error');
    
    if (result.success && result.bucketIndex >= 0) {
        highlightBucket(result.bucketIndex);
    }
}

function resetTable() {
    const bucketSize = parseInt(document.getElementById('bucketSize').value) || 3;
    hashTable = new ExtensibleHashTable(Math.max(1, Math.min(10, bucketSize)));
    renderVisualization();
    showMessage('Tabela reiniciada!', 'info');
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('elementInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            insertElement();
        }
    });
    
    renderVisualization();
});