class Bucket {
    constructor(depth, size) {
    this.localDepth = depth;
    this.items = [];
    this.size = size;
    }

    isFull() {
    return this.items.length >= this.size;
    }
}

class Directory {
    constructor(globalDepth = 1, bucketSize = 2) {
    this.globalDepth = globalDepth;
    this.bucketSize = bucketSize;
    this.table = [];
    const initialBucket = new Bucket(globalDepth, bucketSize);
    for (let i = 0; i < 2 ** globalDepth; i++) {
        this.table[i] = initialBucket;
    }
    }

    hash(key) {
    return parseInt(key) & ((1 << this.globalDepth) - 1);
    }

    doubleDirectory() {
    this.globalDepth++;
    const newTable = [];
    for (let i = 0; i < 2 ** this.globalDepth; i++) {
        newTable[i] = this.table[i % (2 ** (this.globalDepth - 1))];
    }
    this.table = newTable;
    }

    split(index) {
    const oldBucket = this.table[index];
    const newDepth = oldBucket.localDepth + 1;

    const bucket1 = new Bucket(newDepth, this.bucketSize);
    const bucket2 = new Bucket(newDepth, this.bucketSize);

    oldBucket.items.forEach(key => {
        const bit = (parseInt(key) >> (newDepth - 1)) & 1;
        (bit ? bucket2 : bucket1).items.push(key);
    });

    for (let i = 0; i < this.table.length; i++) {
        const match = (i >> (this.globalDepth - newDepth)) === (index >> (this.globalDepth - newDepth));
        if (match) {
        const bit = (i >> (this.globalDepth - newDepth)) & 1;
        this.table[i] = (bit ? bucket2 : bucket1);
        }
    }
    }

    insert(key) {
    if (isNaN(parseInt(key))) return;

    let h = this.hash(key);
    let bucket = this.table[h];

    if (!bucket.items.includes(key)) {
        if (!bucket.isFull()) {
            bucket.items.push(key);
        } else {
            if (bucket.localDepth === this.globalDepth) {
            this.doubleDirectory();
            h = this.hash(key); // recomputa após aumento
            }
            this.split(h);
            this.insert(key); // tenta novamente
        }
    }


    render();
    }
}

const dir = new Directory(1, 2);

// Permite inserir com Enter
document.getElementById('keyInput').addEventListener('keydown', function(event) {
  if (event.key === 'Enter') {
    insertKey();
  }
});


function insertKey() {
    const key = document.getElementById('keyInput').value.trim();
    if (key === '') return;
    dir.insert(key);
    document.getElementById('keyInput').value = '';
}

function removeKey() {
    const key = document.getElementById('keyInput').value.trim();
    if (key === '') return;
    
    const h = dir.hash(key);
    const bucket = dir.table[h];
    const index = bucket.items.indexOf(key);
    
    if (index !== -1) {
        bucket.items.splice(index, 1);
        render();
        alert(`Chave ${key} removida.`);
    } else {
        alert(`Chave ${key} não encontrada.`);
    }

    document.getElementById('keyInput').value = '';
}

function searchKey() {
    const key = document.getElementById('keyInput').value.trim();
    if (key === '') return;

    const h = dir.hash(key);
    const bucket = dir.table[h];

    if (bucket.items.includes(key)) {
        alert(`Chave ${key} encontrada no bucket de índice ${h} (profundidade local: ${bucket.localDepth})`);
    } else {
        alert(`Chave ${key} não encontrada.`);
  }
}

function render() {
    document.getElementById('depth').innerText = dir.globalDepth;
    const container = document.getElementById('directory');
    container.innerHTML = '';

    const seen = new Map();
    dir.table.forEach((bucket, i) => {
    const prefix = i.toString(2).padStart(dir.globalDepth, '0');
    const div = document.createElement('div');
    div.className = 'bucket';

    let content = `<div class="bucket-header">Prefixo: ${prefix}</div>`;

    if (!seen.has(bucket)) {
        content += `<div>LocalDepth: ${bucket.localDepth}</div>`;
        content += `<div class="bucket-items">${bucket.items.map(k => `<div>${k}</div>`).join('')}</div>`;
        seen.set(bucket, div);
    } else {
        content += `<div class="shared">[Bucket compartilhado]</div>`;
    }

    div.innerHTML = content;
    container.appendChild(div);
    });
}

render();