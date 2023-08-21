

import React, { useState } from 'react';
import './App.css';
import axios from 'axios';

function App() {
  const [url, setUrl] = useState('');
  const [shortCode, setShortCode] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const generateShortLink = async () => {
    try {
      if (!isValidUrl(url)) {
        setErrorMessage('Invalid URL');
        return;
      }

      const response = await axios.post('http://localhost:8080/api/generate', { originalUrl: url });
      setShortCode(response.data.shortLink);
      setErrorMessage('');
    } catch (error) {
      setShortCode('');
      setErrorMessage('Error generating short link.');
    }
  };

  const visitShortLink = async () => {
    try {
      
      const response = await axios.get(`http://localhost:8080/api/short-links/${shortCode}`);
      window.location.href = response.data; // Redirect to the original URL
    } catch (error) {
      setErrorMessage('Error visiting the URL.');
    }
  };

  const isValidUrl = (url) => {
    // Basic URL validation logic (you can enhance this)
    const urlPattern = /^(https?|ftp):\/\/[^\s/$.?#].[^\s]*$/i;
    return urlPattern.test(url);
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>URL Shortener</h1>
        <div>
          <input
            type="text"
            placeholder="Enter URL"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
          />
          <button onClick={generateShortLink}>Generate Short Link</button>
        </div>
        {errorMessage && <p className="error">{errorMessage}</p>}
        {shortCode && (
          <div>
            <p>
              Short URL: <a href={`http://localhost:8080/api/short-links/${shortCode}`} target="_blank" rel="noopener noreferrer">{shortCode}</a>
            </p>
            <button onClick={visitShortLink}>Visit Short Link</button>
          </div>
        )}
      </header>
    </div>
  );
}

export default App;

