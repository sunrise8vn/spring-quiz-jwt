let days = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
let months = ['January','February','March','April','May','June','July','August','September','October','November','December'];

function padTo2Digits(num) {
    return num.toString().padStart(2, '0');
}

function formatDate(date) {
    const targetDay = new Date(date);
    const day = days[targetDay.getDay()];
    const month = months[targetDay.getMonth()];
    let str = day + ', ' + String(targetDay.getDate()).padStart(2, '0') + ' ' + month + ' ' + targetDay.getFullYear() + ', ';
    str += targetDay.toLocaleTimeString('en-US');

    return str;

    // return (
    //     [
    //         padTo2Digits(date.getMonth() + 1),
    //         padTo2Digits(date.getDate()),
    //         date.getFullYear(),
    //     ].join('/') +
    //     ' ' +
    //     [
    //         padTo2Digits(date.getHours()),
    //         padTo2Digits(date.getMinutes()),
    //         padTo2Digits(date.getSeconds()),
    //     ].join(':')
    // );
}