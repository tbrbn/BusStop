function fetchData() {
  fetch('output.json')
    .then((res) => res.json())
    .then((data) => {
      console.log(data)
      let output = '<h2">Top 10 Bus Lines with most stops</h2>'
      data.forEach(function (item) {
        console.log(item)
        output += `
        <ul>
          <li>Line: ${item.Line}</li>
          <li>Stop: ${item.Stops}</li>
        </ul>
      `
      })
      document.getElementById('BusLines').innerHTML = output
    })
    .catch((error) => {
      console.log(`Error Fetching data : ${error}`)
      document.getElementById('BusLines').innerHTML = 'Error Loading Data - try refreshing your folders'
    })
}

function test(){
    fetch('output.json')
        .then(
            res => {
                console.log(res.json())})
}
alert("Done!")
fetchData()
