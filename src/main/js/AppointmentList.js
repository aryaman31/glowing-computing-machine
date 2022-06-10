import Appointment from './Appointment'

export default function AppointmentList({ upcomings, setUpcoming }) {

  function handleCancel(k) {
    const temp = [...upcomings]
    
    setUpcoming(temp.filter(elem => elem.id !== k))
  }

  return (
    upcomings.map(a => {
        return (
          <div key={a.id}>
            <Appointment appointment={a}/>

            <button onClick={() => handleCancel(a.id)}>
              Cancel appointment
            </button>

          </div>

        )
    })
  )
}
