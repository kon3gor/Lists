function onItemKeyPressed(event, id) {
      if (event.key === "Enter") {
        event.preventDefault();
        htmx.trigger(`#input${id}`, "rename", {})
        disableEditFor(id)
        document.activeElement.blur()
      }
}

function enableEditFor(id) {
    var target = document.getElementById(`input${id}`)
    target.classList.add("item-title-enabled")
}

function disableEditFor(id) {
    var target = document.getElementById(`input${id}`)
    target.classList.remove("item-title-enabled")
}

function clearInput() {
    document.getElementById("new-item-input").value = ""
}

function getItemName(id) {
    var target = document.getElementById(`input${id}`)
    return target.innerHTML
}