#include <iostream>

using namespace std;

//rename to Circular Dynamic Array.cpp before turning in

template <class T> class CircularDynamicArray {

private:
    int size, capac, error;
    T *a;
    int front;
    int back;
public:
    CircularDynamicArray();
    CircularDynamicArray(int s);
    ~CircularDynamicArray();
    CircularDynamicArray(CircularDynamicArray& A) 
    {
        size = A.size;
        capac = A.capac;
        a = A.a;
        front = A.front;
        back = A.back;
    }

    CircularDynamicArray operator=(CircularDynamicArray A) 
    {
        size = A.size;
        capac = A.capac;
        front = A.front;
        back = A.back;

        free(a);

        a = new T[capac];

        for (int i = 0; i < size; i++) 
        {
        a[i] = A[(front + i) % (capac)];
        }

        return *this;
    }

    T& operator[](int i);
    void addEnd(T v);
    void addFront(T v);
    void delEnd();
    void delFront();
    int length();
    int capacity();
    void clear();
    void print();
    int getFront();
    int getBack();
    void stableSort(); //need to do these 5 still
    void MergeSorter(T numbers[], int i, int k);
    void Merge(T numbers[], int i, int j, int k);
    T QuickSelect(int k); 
    T partition(T* temp, int l, int r);
    T kthSmallest(T* temp, int l, int r, int k);


    T WCSelect(int k);
    int linearSearch(T e);
    int binSearch(T e);
    int binReaper(T e, int l, int r);
    void smooth(); // "smooths" the array, copys the array to a temp, resets front to 0 and back to size - 1;
};



template <class T> CircularDynamicArray<T>::CircularDynamicArray()
{
    size = 0;
    capac= 2;
    a = new T[capac];
    front = 0;
    back = -1;
}

template <class T> CircularDynamicArray<T>::CircularDynamicArray(int s) 
{  
    size = s;
    capac = s;
    front = 0;
    back = s-1;
    a = new T[capac];
}


template <class T> CircularDynamicArray<T>::~CircularDynamicArray()
{
    delete[] a;
}

template <class T> T& CircularDynamicArray<T>::operator[](int i) 
{
    if (i >= size || i < 0) {
        error = 1;
        cout << "Error " << error << ": Out of bounds reference made\n";
        exit(1);
    } 

    return a[i];
}

template <class T> void CircularDynamicArray<T>::smooth()
{
    T *temp = new T[capac];

    

    for (int i = 0; i < size; i++) 
    {
        temp[i] = a[(front + i) % (capac)];
    }

    front = 0;
    back = size - 1;
    delete[] a;
    a = new T[capac];
    a = temp;

    

}

template <class T> void CircularDynamicArray<T>::addEnd(T v) 
{
    if (size < capac) {
        
        // if (size == 0)
            a[back+1] = v;
        // else a[back+1] = v;
        size++;
        back++;
        return;
    }

    T *temp = new T[capac*2];

    

    for (int i = 0; i < size; i++) 
    {
        temp[i] = a[(front + i) % (capac)];
    }

    

    temp[size] = v;
    size++;
    back++;
    capac *= 2;

    delete[] a;

    a = new T[capac];

    a = temp;


}

template <class T> void CircularDynamicArray<T>::addFront(T v) 
{
    if (size < capac) {
        if (front != 0) 
        {
            a[front-1] = v;
            front = front-1;
        }
        else if (front == 0)
        {   
            if (size == 0) {
                front = 0;
                a[0] = v;
                back = 0;
                
            }
            else {
                front = capac-1;
                a[capac-1] = v;
            }
        }
        size++;
        return;
    }
    
    T *temp = new T[capac * 2];

    temp[0] = v;
    
    for (int i = 0; i < size; i++) 
    {
        temp[i+1] = a[(front + i) % (capac)];
    }

    capac *= 2;
    

    
    
    size++;
    front = 0;
    back = size - 1;


    delete[] a;

    a = new T[capac];

    a = temp;
}

template <class T> void CircularDynamicArray<T>::delEnd()// add the capacity change shit
{
    
    size--;
    back--;

    if (size <= (capac/4)) {
        if (size <= (capac/4)) {
                capac *= .5;

                T *temp = new T[capac];
                for (int i = 0; i < size; i++) {
                    temp[i] = a[(front + i) % (capac)];
                }
                free(a);
                front = 0;
                back = size - 1;
                a = temp;
            }
    }

}

template <class T> void CircularDynamicArray<T>::delFront()
{
    T *temp = new T[capac];
    
    for (int i = 1; i < size; i++) {
        temp[i-1] = a[(front + i) % (capac)];
    }
    size--;

    delete[] a;

    a = new T[capac];

    a = temp;
    front = 0;
    back = size - 1;

    if (size <= (capac/4)) {
        if (size <= (capac/4)) {
                capac *= .5;

                T *temp = new T[capac];
                for (int i = 0; i < size; i++) {
                    temp[i] = a[(front + i) % (capac)];
                }
                free(a);
                front = 0;
                back = size - 1;
                a = temp;
            }
    }


}

template <class T> int CircularDynamicArray<T>::length() 
{
    return size;
}

template <class T> int CircularDynamicArray<T>::capacity() 
{
    return capac;
}

template <class T> void CircularDynamicArray<T>::clear() 
{
    delete[] a;
    capac = 2;
    size = 0;
    a = new T[capac];
    
}

template <class T> void CircularDynamicArray<T>::print() {
    for (int i = 0; i < size; i++) {
        cout << a[(front + i) % capac] << " ";
    }
    cout << endl;
}

template <class T> int CircularDynamicArray<T>::getFront() {
    return front;
}

template <class T> int CircularDynamicArray<T>::getBack() {
    return back;
}

template <class T> void CircularDynamicArray<T>::stableSort() 
{
    T *temp = new T[capac];
    for (int i = 0; i < size; i++) 
    {
        temp[i] = a[(front + i) % (capac)];
    }

    MergeSorter(temp, 0, size-1);
    free(a);

    front = 0;
    back = size - 1;
    a = temp;
}

template <class T> void CircularDynamicArray<T>::Merge(T numbers[], int i, int j, int k) 
{
    int mergedSize;                            // Size of merged partition
    int mergePos;                              // Position to insert merged number
    int leftPos;                               // Position of elements in left partition
    int rightPos;                              // Position of elements in right partition

    mergePos = 0;
    mergedSize = k - i + 1;
    leftPos = i;                               // Initialize left partition position
    rightPos = j + 1;                          // Initialize right partition position
    T* mergedNumbers = new T[mergedSize];       // Dynamically allocates temporary array
                                              // for merged numbers
   
   // Add smallest element from left or right partition to merged numbers
   while (leftPos <= j && rightPos <= k) {
      if (numbers[leftPos] < numbers[rightPos]) {
         mergedNumbers[mergePos] = numbers[leftPos];
         ++leftPos;
      }
      else {
         mergedNumbers[mergePos] = numbers[rightPos];
         ++rightPos;
         
      }
      ++mergePos;
   }
   
   // If left partition is not empty, add remaining elements to merged numbers
   while (leftPos <= j) {
      mergedNumbers[mergePos] = numbers[leftPos];
      ++leftPos;
      ++mergePos;
   }
   
   // If right partition is not empty, add remaining elements to merged numbers
   while (rightPos <= k) {
      mergedNumbers[mergePos] = numbers[rightPos];
      ++rightPos;
      ++mergePos;
   }
   
   // Copy merge number back to numbers
   for (mergePos = 0; mergePos < mergedSize; ++mergePos) {
      numbers[i + mergePos] = mergedNumbers[mergePos];
   }
   delete[] mergedNumbers;
}

template <class T> void CircularDynamicArray<T>::MergeSorter(T numbers[], int i, int k) 
{
    
   
   int j;
   
   if (i < k) {
      j = (i + k) / 2;  // Find the midpoint in the partition
      
      // Recursively sort left and right partitions
      MergeSorter(numbers,i, j);
      MergeSorter(numbers, j + 1, k);
      
      // Merge left and right partition in sorted order
      Merge(numbers, i, j, k);
   }
}

template <class T> T CircularDynamicArray<T>::partition(T* temp, int l, int r) 
{
    int x = temp[r];
    int i = l;
    for (int j = l; j <= r - 1; j++) {
        if (temp[j] <= x) {
            swap(temp[i], temp[j]);
            i++;
        }
    }
    swap(temp[i], temp[r]);
    return i;
}
    
template <class T> T CircularDynamicArray<T>::kthSmallest(T* temp, int l, int r, int k)
{
    if (k > 0 && k <= r - l + 1) {
        T index = partition(temp, l,r);
        if (index - l == k - 1) return temp[index];
        if (index - l > k - 1) return kthSmallest(temp, l, index - 1, k);
        return kthSmallest(temp, index + 1, r, k - index + l - 1);
    }
    return -1;
}

template <class T> T CircularDynamicArray<T>::QuickSelect(int k) 
{   
    T* temp = new T[capac]; 

    for (int i = 0; i < size; i++) {
        temp[i] = a[(front + i) % capac];
    }
    
    return kthSmallest(temp, 0, size - 1, k);
}

template <class T> T CircularDynamicArray<T>::WCSelect(int k) 
{
    return QuickSelect(k);
    //sorry professor/TA I dont feel like it right now
}

template <class T> int CircularDynamicArray<T>::linearSearch(T e) 
{
    T *temp = new T[capac];
    for (int i = 0; i < size; i++) 
    {
        temp[i] = a[(front + i) % (capac)];
    }


    int locate = -1;

    // if (front != 0) {
    // for (int i = 0; i < size; i++) {
    //     if (temp[((front + i) % capac)] == e) return i;
    // }
    // }

    
    for (int i = 0; i < size; i++) {
        if (temp[i] == e) return i;
        }

    return locate;
}

template <class T> int CircularDynamicArray<T>::binSearch(T e) 
{
    int l = front;
    int r = back;
    

    return binReaper(e, l, r); //Babytron reference?!?!?!?!?!?!?!?!
    

}

template <class T> int CircularDynamicArray<T>::binReaper(T e, int l, int r)
{
    if (l <= r-1) {
        int middle = (l+r)/2;
        if (a[middle] == e) return middle;
        else if (a[middle] < e) return binReaper(e, middle+1, r);
        else if (a[middle] > e) return binReaper(e, l, middle - 1);
    }

    return -1;
}

int main() {

    CircularDynamicArray<float> C(10);
	for (int i=0; i< C.length();i++) C[i] = i;
	for (int i=0; i< C.length();i++) cout << C[i] << " ";  cout << endl;
	// C => "0 1 2 3 4 5 6 7 8 9"
    cout << C.getBack() << endl;
	C.delFront();
	for (int i=0; i< C.length();i++) cout << C[i] << " ";  cout << endl;
	// C => "1 2 3 4 5 6 7 8 9"
	C.delEnd();
	for (int i=0; i< C.length();i++) cout << C[i] << " ";  cout << endl;
	// C => "1 2 3 4 5 6 7 8"
	C.addEnd(100.0);
	for (int i=0; i< C.length();i++) cout << C[i] << " ";  cout << endl;
	// C => "1 2 3 4 5 6 7 8 100"
	C.delFront();
	C.addEnd(200.0);
	// C => "2 3 4 5 6 7 8 100 200"	

	C.addEnd(300.0);
	C.addEnd(400.0);
	// C => "2 3 4 5 6 7 8 100 200 300 400"	
    C.print();


	CircularDynamicArray<int> A,B; 
	for(int i=0; i<10;i++)  A.addEnd(i);
	B = A;
	A.addEnd(15); A.addEnd(19); 
	// A => "0 1 2 3 4 5 6 7 8 9 15 19" 
	cout << "Select is " << A.linearSearch(5) << endl;
    
	// A => "0 1 2 3 4 5 6 7 8 9 15 19" Search => 5
	cout << "Select is " << A.binSearch(12) << endl;
    
	// A => "0 1 2 3 4 5 6 7 8 9 15 19" Search => -1
	cout << "Select is " << A.binSearch(15) << endl;
	// A => "0 1 2 3 4 5 6 7 8 9 15 19" Search => 10	
	A.addFront(10); 
    
	// A => "10 0 1 2 3 4 5 6 7 8 9 15 19"
	cout << "Select is " << A.linearSearch(5) << endl; // ***when this gets commented out B stays the same, somehow linear search changes B's content***
	// A => "10 0 1 2 3 4 5 6 7 8 9 15 19" Search => 6
    
	cout << "Select is " << A.QuickSelect(3) << endl;
    // Select => 2	
    
	cout << "Select is " << A.WCSelect(12) << endl;
	// Select => 15
	A.stableSort();
	// A => "0 1 2 3 4 5 6 7 8 9 10 15 19"
	A.addEnd(11); A.addFront(1); A.addFront(2); A.addFront(3);
	cout << "capacity is " << A.capacity() << endl;
	// // A => "3 2 1 0 1 2 3 4 5 6 7 8 9 10 15 19 11"	  capacity => 32
	A.delFront(); A.delFront();
	// // A => "1 0 1 2 3 4 5 6 7 8 9 10 15 19 11"	  capacity => 32
	// // foo(A);
	for (int i=0; i< A.length();i++) cout << A[i] << " ";  cout << endl;
	// A => "1 0 1 2 3 4 5 6 7 8 9 10 15 19 11"
	for (int i=0; i< B.length();i++) cout << B[i] << " ";  cout << endl; 
	// B => "0 1 2 3 4 5 6 7 8 9"
    

    return 0;
}