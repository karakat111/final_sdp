'use strict';
const $ = id => document.getElementById(id);
const choice = () => document.querySelector('input[name="trip"]:checked').value;
const params = () => new URLSearchParams({
  trip: choice(), strategy: $('strategy').value,
  insurance: String($('insurance').checked), luggage: String($('luggage').checked)
});
const money = n => new Intl.NumberFormat('en-US', {style:'currency',currency:'USD'}).format(n);
const message = (text, type='') => { $('message').textContent = text; $('message').className = type; };
let quoteVersion = 0;
async function updateQuote() {
  const version = ++quoteVersion;
  $('book').disabled = true;
  $('price').textContent = 'Calculating…';
  message('');
  try {
    const response = await fetch('/api/quote?' + params());
    const data = await response.json();
    if (!response.ok) throw Error(data.error || 'Could not calculate price');
    if (version !== quoteVersion) return;
    $('price').textContent = money(data.price);
    $('destination').textContent = data.destination;
    $('book').disabled = false;
  } catch (e) { if (version === quoteVersion) { $('price').textContent = 'Unavailable'; message(e.message, 'error'); } }
}
document.querySelectorAll('input[name="trip"], #strategy, #insurance, #luggage').forEach(el => el.addEventListener('change', updateQuote));
$('book').addEventListener('click', async () => {
  $('book').disabled = true;
  message('Booking…');
  const p = params();
  p.set('name', $('name').value.trim());
  p.set('email', $('email').value.trim());
  try {
    const response = await fetch('/api/book', {method:'POST',headers:{'Content-Type':'application/x-www-form-urlencoded'},body:p.toString()});
    const data = await response.json();
    if (!response.ok) throw Error(data.error || 'Booking failed');
    $('price').textContent = money(data.price);
    message('✓ Booking confirmed! ' + data.destination + ' · ' + money(data.price) + '. Total bookings this session: ' + data.bookingCount, 'success');
  } catch (e) { message(e.message, 'error'); }
  finally { $('book').disabled = false; }
});
updateQuote();
