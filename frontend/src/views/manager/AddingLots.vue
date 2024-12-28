<template>
    <div class="adding-lots">
      <h1>Add New Parking Lot</h1>
      <form @submit.prevent="submitForm">
        <!-- Lot Name -->
        <div class="form-group">
          <label for="location">Lot Location:</label>
          <input type="text" id="location" v-model="location" required />
        </div>
        <div class="form-group">
          <label for="longitude">longitude:</label>
          <input type="text" id="longitude" v-model="longitude" required />
        </div>
        <div class="form-group">
          <label for="latitude">latitude:</label>
          <input type="text" id="latitude" v-model="latitude" required />
        </div>
        <!-- Capacity -->
        <div class="form-group">
          <label for="capacity">Capacity:</label>
          <input type="number" id="capacity" v-model="capacity" required />
        </div>
  
        <!-- Pricing Structure -->
        <div class="form-group">
          <label for="pricingStructure">Pricing Structure:</label>
          <input type="text" id="pricingStructure" v-model="pricingStructure" required />
        </div>
  
        <!-- Types of Spots -->
        <!-- <div class="form-group"> -->
          <label for="typesOfSpots">Types of Spots:</label>
          <div>
            <label>
              <input type="checkbox" value="Regular" v-model="isThereRegular" /> Regular
              <input v-if="isThereRegular" type="number" id="numberOfRegular" v-model="numberOfRegular"/> 
            </label>
            <label>
              <input type="checkbox" value="EV Charging" v-model="isThereElectric" /> EV Charging
                <input v-if="isThereElectric" type="number" id="numberOfElectric" v-model="numberOfElectric"/>
            </label>
            <label>
              <input type="checkbox" value="Disabled" v-model="isThereDisabilities" /> Disabled
                <input v-if="isThereDisabilities" type="number" id="numberOfDisabilities" v-model="numberOfDisabilities"/>
            </label>
          </div>
  
        <button type="submit">Add Lot</button>
      </form>
    </div>
  </template>
  
  <script>
  import { parkingLotsAPI } from '../../api/parkingLots';
  import { parkingSpotsAPI } from '../../api/parkingSpots';
  export default {
    data() {
      return {
        location: '',
        capacity: '',
        pricingStructure: '',
        typesOfSpots: [],
        latitude: null,
        longitude: null,
        spots: [],
        isThereRegular: false,
        isThereElectric: false,
        isThereDisabilities: false,
        numberOfRegular: 0,
        numberOfElectric: 0,
        numberOfDisabilities: 0
      };
    },
    // mounted() {
    //   this.initMap();
    // },
    methods: {
      // Initialize Google Maps
    //   initMap() {
    //     const map = new google.maps.Map(document.getElementById('map'), {
    //       center: { lat: -34.397, lng: 150.644 },
    //       zoom: 8,
    //     });
  
        // map.addListener('click', (e) => {
        //   this.latitude = e.latLng.lat();
        //   this.longitude = e.latLng.lng();
        // });
    //   },
  
      // Handle Form Submission
      submitForm() {
        if (!this.location || !this.capacity || !this.pricingStructure) {
          alert('Please fill out all fields');
          return;
        }
        
        if(this.latitude === null || this.longitude === null){
          alert('Please select the location on the map');
          return;
        }
        if(this.numberOfDisabilities+this.numberOfElectric+this.numberOfRegular !== this.capacity){
            alert('The number of spots does not match the capacity');
            return;
        }
        if(this.isThereRegular){
            this.typesOfSpots.push('Regular');
        }else if(!this.isThereRegular && this.typesOfSpots.includes('Regular')){
            this.typesOfSpots = this.typesOfSpots.filter(item => item !== 'Regular');
        }
        if(this.isThereElectric){
            this.typesOfSpots.push('EV Charging');
        }
        else if(!this.isThereElectric && this.typesOfSpots.includes('EV Charging')){
            this.typesOfSpots = this.typesOfSpots.filter(item => item !== 'EV Charging');
        }
        if(this.isThereDisabilities){
            this.typesOfSpots.push('Disabled');
        } else if(!this.isThereDisabilities && this.typesOfSpots.includes('Disabled')){
            this.typesOfSpots = this.typesOfSpots.filter(item => item !== 'Disabled');
        }
        if (this.typesOfSpots.length === 0) {
          alert('Please select at least one type of spot');
          return;
        }
        if(this.isThereRegular && this.numberOfRegular === 0){
            this.typesOfSpots = [];
          alert('Please fill out the number of regular spots');
          return;
        }
        if(this.isThereElectric && this.numberOfElectric === 0){
            this.typesOfSpots = [];
          alert('Please fill out the number of EV Charging spots');
          return;
        }
        if(this.isThereDisabilities && this.numberOfDisabilities === 0){
            this.typesOfSpots = [];
          alert('Please fill out the number of Disabled spots');
          return;
        }

        this.uploadLot();
 
      },
  
      // Reset the form after successful submission
      resetForm() {
        this.location = '';
        this.capacity = '';
        this.latitude = null;
        this.longitude = null;
        this.pricingStructure = '';
        this.typesOfSpots = [];
        this.numberOfRegular = 0;
        this.numberOfElectric = 0;
        this.numberOfDisabilities = 0;
        this.isThereDisabilities = false;
        this.isThereElectric = false;
        this.isThereRegular = false;
    },

    async uploadLot() {
        const newLot = {
            location: this.location,
            capacity: this.capacity,
            latitude: this.latitude,
            longitude: this.longitude,
            pricingStructure: this.pricingStructure,
            typesOfSpots: this.typesOfSpots.join(','),
        };
        console.log('New Lot:', newLot);
        const lot_Id = await parkingLotsAPI.addLot(newLot);
        console.log('Response of adding a New Lot: ', lot_Id);
        // if (response.error) {
        //   alert('Failed to add parking lot');
        //   return;
        // }
        // console.log('Parking lot added successfully', response);
        for(let i = 0; i < this.typesOfSpots.length; i++){
            let type = this.typesOfSpots[i];
            let spot = {
                n: 0,
                type: type,
                lotId: lot_Id,
            };
            if(type === 'Regular'){
                spot.n = this.numberOfRegular;
            } else if(type === 'EV Charging'){
                spot.n = this.numberOfElectric;
            } else if(type === 'Disabled'){
                spot.n = this.numberOfDisabilities;
            }
            let response = await parkingSpotsAPI.createSpot(spot);
            console.log('Response of adding a New Spot: ', response);
            if (response.error) {
              alert('Failed to add spot');
              return;
            }
            console.log('Spot added successfully', response);
          }
        alert('Parking lot added successfully with it\'s spots');
        this.resetForm();
      }
    },
    
  };
  </script>
<!--   
  <style scoped>
  .adding-lots {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 5px;
  }
  
  .form-group input,
  .form-group select {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
  }
  
  button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  table th,
  table td {
    border: 1px solid #ccc;
    padding: 8px;
    text-align: center;
  }
  
  table th {
    background-color: #f4f4f4;
  }
  </style>
   -->

   <style scoped>
.adding-lots {
  max-width: 600px;
  margin: 40px auto;
  padding: 30px;
  border: 1px solid #ddd;
  border-radius: 12px;
  background-color: #ffffff;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 8px;
  color: #555;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
}

.form-group input:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

button {
  display: block;
  width: 100%;
  padding: 12px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

button:active {
  transform: scale(0.98);
}

.form-group div {
  display: flex;
  gap: 15px;
  align-items: center;
  margin-top: 8px;
}

.form-group input[type="checkbox"] {
  width: auto;
  margin-right: 8px;
}

input[type="number"] {
  width: 80px;
}

/* Add spacing between form sections */
.form-group:nth-child(3) {
  margin-bottom: 25px;
}

/* Improve the alignment of checkboxes and inputs */
label input[type="checkbox"] {
  margin-right: 10px;
}

label input[type="number"] {
  margin: 5px;
  padding: 4px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

/* Highlight alerts */
.alert {
  padding: 15px;
  margin-bottom: 20px;
  border: 1px solid transparent;
  border-radius: 6px;
  color: #fff;
  background-color: #ff6b6b;
  font-weight: bold;
  text-align: center;
}
</style>
