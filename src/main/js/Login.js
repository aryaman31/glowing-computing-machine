import React, { useState } from 'react'
import { Link } from 'react-router-dom';

export default function Login({ name, setName }) {

  const [password, setPassword] = useState(''); 


  function handleSubmit(event) {
    event.preventDefault();

    //TODO: add to database
    console.log(name);
    console.log(password);

  }

  return (
      <>
      <h1>Please enter your name and a password to revisit your session</h1>
      <form onSubmit={handleSubmit}>
          <div>
              <label>Name</label>
                <input
                id="name"
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                />
          </div>
          <div>
                <label>Password</label>
                <input
                id="password"
                type="text"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                />
          </div>
          <Link to='/home'>
            <button type="submit">Submit</button>
          </Link>
      </form>
      </>
    
  )
}
