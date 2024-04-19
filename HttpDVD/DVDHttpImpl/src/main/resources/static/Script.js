// Function to fetch DVDs from the backend
function fetchDVDs() {
    fetch('/dvds')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch DVDs');
            }
            return response.json();
        })
        .then(data => {
            const dvdsList = document.getElementById('dvds-list');
            dvdsList.innerHTML = '';
            data.dvds.forEach(dvd => {
                const listItem = document.createElement('li');
                listItem.classList.add('dvd-item');
                listItem.textContent = `${dvd.title} (${dvd.releaseYear}) - ${dvd.director}`;
                listItem.addEventListener('click', function() {
                    selectDVD(listItem);
                });
                dvdsList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error fetching DVDs:', error));
}

// Function to select a DVD for deletion
function selectDVD(dvd) {
    const deleteButton = document.getElementById('delete-dvd');
    deleteButton.disabled = false;
    deleteButton.dataset.dvdId = dvd.textContent.split(' ')[0]; // Set DVD title as data attribute

    // Remove 'selected' class from all other items
    const allItems = document.querySelectorAll('.dvd-item');
    allItems.forEach(item => {
        item.classList.remove('selected');
    });

    // Add 'selected' class to the clicked item
    dvd.classList.add('selected');
}

// Function to delete a DVD
async function deleteDVD() {
    const dvdTitle = document.getElementById('delete-dvd').dataset.dvdId;
    try {
        const response = await fetch(`/dvds/${encodeURIComponent(dvdTitle)}`, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error('Failed to delete DVD');
        }
        console.log('DVD deleted successfully');
        // Refresh the DVD list after deletion
        fetchDVDs();
    } catch (error) {
        console.error('Error deleting DVD:', error);
    }
}

// Add event listener for form submission
document.getElementById('add-dvd-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const dvdData = Object.fromEntries(formData.entries());
    const releaseYear = parseInt(dvdData.releaseYear);
    if (isNaN(releaseYear) || releaseYear < 1000 || releaseYear > 9999) {
        alert('Release year must be an integer with 4 digits.');
        return;
    }

    // Convert releaseYear to an integer
    dvdData.releaseYear = releaseYear;
    fetch('/dvds', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dvdData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to add DVD');
        }
        console.log('DVD added successfully');
        fetchDVDs(); // Fetch DVDs again to update the list
    })
    .catch(error => console.error('Error adding DVD:', error));
});

// Add event listener for delete button
document.getElementById('delete-dvd').addEventListener('click', deleteDVD);

// Add event listener for filtering DVDs by title
document.getElementById('filter-title').addEventListener('input', function() {
    const filter = this.value.toLowerCase();
    const dvds = document.querySelectorAll('#dvds-list li');
    dvds.forEach(dvd => {
        const title = dvd.textContent.toLowerCase();
        if (title.includes(filter)) {
            dvd.style.display = 'block';
        } else {
            dvd.style.display = 'none';
        }
    });
});

// Fetch DVDs when the page loads
document.addEventListener('DOMContentLoaded', fetchDVDs);
