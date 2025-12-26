import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId,String brand , String model, double basePricePerDay){
        this.carId=carId;
        this.brand=brand;
        this.model=model;
        this.basePricePerDay=basePricePerDay;
        this.isAvailable=true;

    }
    public String getcarId(){
        return carId;
    }
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public double CalculatePrice(int rentalDays){
        return basePricePerDay*rentalDays;

    }
    public boolean isAvailable(){
        return isAvailable;

    }
    public void rent(){

        isAvailable=false;
    }

    public void returnCar(){
        isAvailable=true;
    }




}

class Customer{

    private String customerid;
    private String name;
    public Customer(String customerid,String name){
        this.customerid=customerid;
        this.name=name;

    }

    public String getCustomerid(){
        return customerid;
    }

    public String getName(){
        return name;
    }


}
class Rental{

    private Car car;
    private Customer customer;

    private int days;

    public Rental(Car car,Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }

    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }


}

class CarRentalSystem{
    private List<Car> cars;
    
    private List<Customer>customers;
    private List<Rental>rentals;

    public CarRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();


    }
     public void addCar(Car car){
        cars.add(car);
     }

     public void addCustomer(Customer customer){
        customers.add(customer);

     }

     public void rentCar(Car car,Customer customer ,int Days){
        if (car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, Days));
        }else{
            System.out.println("Car is not available for rent.");
        }
     }
     
     public void returnCar(Car car){
        car.returnCar();
        Rental rentalToRemove=null;
        for(Rental rental:rentals){
            if(rental.getCar()==car){
                rentalToRemove=rental;
                break;
            }
        }
        if(rentalToRemove !=null){
            rentals.remove(rentalToRemove);
            System.out.println("Car returned Sucessfully.");
        }else{
            System.out.println("Car was not Rented.");
        }
     }
     public void menu(){
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("==========Car Rental System Menu==========");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice:");
            int choice=sc.nextInt();
            sc.nextLine();
            if (choice==1){
            
                    System.out.println("\n======Rent A car=========\n");
                    System.out.println("Enter your name:");
                    String customerName=sc.nextLine();
                    System.out.println("\nAvailable Cars:");
                    for(Car car:cars){
                        if(car.isAvailable()){
                            System.out.println(car.getcarId()+"-"+car.getBrand()+" "+car.getModel());

                        }
                    }

                    System.out.println("Enter Car ID to rent:");
                    String carId=sc.nextLine();
                    System.out.println("Enter number of rental days:");
                    int rentalDays=sc.nextInt();
                    sc.nextLine();

                    Customer newCustomer=new Customer("cus"+(customers.size()+1), customerName);
                    addCustomer(newCustomer);

                    
                    Car carToRent=null;
                    for(Car car:cars){
                        if(car.getcarId().equals(carId)&&car.isAvailable()){
                            carToRent=car;
                            break;
                        }
                    }
                      if(carToRent!=null){
                        double totalPrice=carToRent.CalculatePrice(rentalDays);
                        System.out.println("\n== Rentals Information ==\n");
                        System.out.println("Customer ID:"+newCustomer.getCustomerid());
                        System.out.println("Customer Name:"+newCustomer.getName());
                        System.out.println("Car:"+carToRent.getBrand()+" "+carToRent.getModel());
                        System.out.println("Rental Days: "+rentalDays);
                        System.out.println("Total Price : $%.2f%n"+totalPrice);

                        System.out.println("\n Confirm rental(Y/N):");
                        String confirm=sc.nextLine();
                        if(confirm.equalsIgnoreCase("Y")){
                            rentCar(carToRent, newCustomer, rentalDays);
                            System.out.println("\n Car rented Successfully.");
                        }else{
                            System.err.println("\n Rental Successfully..");

                        
                        }
                      }else{
                        System.out.println("\n Invalid car selection or car not avialable for rent.");
                      }    

                        
                }else if(choice==2){
                    System.out.println("\n===Return a car====\n");
                    System.out.println("Enter Car ID to return:");
                    String returnCarId=sc.nextLine();

                    Car carToReturn=null;
                    for(Car car:cars){
                        if(car.getcarId().equals(returnCarId)&& !car.isAvailable()){
                            carToReturn=car;
                            break;
                        }
                    }
                    if(carToReturn !=null){
                        Customer customer=null;
                        for (Rental rental:rentals){
                            if(rental.getCar()==carToReturn){
                                  customer=rental.getCustomer();
                                  break;

                            }
                        }

                        if(customer !=null){
                            returnCar(carToReturn);
                            System.out.println("Car returned Sucessfully by"+customer.getName());
                        }else{
                            System.out.println("Car was not rented or rental information is missing");
                        }
                    }else{
                        System.out.println("Invalid car id or car is not rented.");
                    }


                }else if(choice==3){
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                }else{
                     System.out.println("Invalid choice. Please try again.");

                }
            }
                   


                 
      
       System.out.println("\nThank you for using the Car Rental System!");

    }
    
}



public class Main{
    public static void main(String[] args){
        CarRentalSystem rentalsystem=new CarRentalSystem();
        Car car1=new Car("C001","Toyota","Camry",50.0);
        Car car2=new Car("C002","Honda","Civic",45.0);
        Car car3=new Car("C003","Ford","Focus",40.0);
        rentalsystem.addCar(car1);
        rentalsystem.addCar(car2);
        rentalsystem.addCar(car3);
        rentalsystem.menu();
    }
}
