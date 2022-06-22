import React, { useState } from 'react'
import { useNavigate } from 'react-router';
import { Link } from 'react-router-dom';

export default function AdminLogin({ adminName, setAdminName }) {

  const navigate = useNavigate();

  const [password, setPassword] = useState(''); 


  function handleSubmit(event) {
    event.preventDefault();

    //TODO: add to database
    console.log(name);
    console.log(password);

    navigate('/admin_home')
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
                value={adminName}
                onChange={(e) => setAdminName(e.target.value)}
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

          <button type="submit">Submit</button>

          <Link to="/">
            <button>Back</button>
          </Link>

      </form>

    </>

    
  )
}
