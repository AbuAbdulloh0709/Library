function activateItem(event) {
    var itemList = document.querySelectorAll('li');


    // Add active class to the clicked item
    var clickedItem = event.target;
    clickedItem.classList.add('active');
}
