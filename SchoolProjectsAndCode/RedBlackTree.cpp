#include <iostream>
#include <string.h>
#include <stdlib.h>
#include <time.h>
using namespace std;



template <class keytype, class valuetype>
class RBTree { 
    public:
    struct node {
        node *L;
        node *R; 
        node *P;
        int isRed;
        int size; 
        keytype key;
        valuetype info;
    };
    typedef node *NP; 
    NP root;
    NP NIL; 
    int TreeSize;
    int Rank;
    int selectRank;
    
    
    RBTree () { //default constructor
        NIL = new node; //current node
        NIL->L = nullptr;
        NIL->R = nullptr;
        NIL->P = nullptr;
        NIL->isRed = 0;
        root = NIL;
        TreeSize = 0;
    } //WORKING
    RBTree(keytype k[], valuetype V[], int s) { //constructor
        NIL = new node;
        NIL->isRed = 0;
        NIL->L = nullptr;
        NIL->R = nullptr; 
        root = NIL;
        for (int i = 0; i < s; i++) {
            insert(k[i], V[i]);
            TreeSize++;
        }
    } //WORKING

    RBTree(const RBTree &x) { //copy constructor
        this->NIL = new node;
        this->TreeSize = 0;
        this ->NIL->isRed = 0;

        if (x.root == x.NIL) {
            this->root = this->NIL;
        }
        else {
            treeCopy(this->root, x.root, x.NIL, this->NIL, nullptr);
        }
    }
    RBTree operator=(const RBTree x) {
        this->TreeSize = 0;
        if (this != &x) {
            if (root != NIL) {
                delete this->root;
            }
            treeCopy(this->root, x.root, x.NIL, this->NIL, nullptr);
        }
        return *this;
    }
    void treeCopy(NP &y, NP x, NP NIL1, NP NIL2, NP z) {
        if (x == NIL1) {
            y = NIL2;
        }
        else {
            y = new node;
            y->key = x->key;
            y->info = y->info;
            y->isRed = y->isRed;
            y->size = x->size;
            if (y->L != NIL) {
                y->L = x->L;
            }
            if (y->R != NIL) {
                y->R = x->R;
            }
            y->P = z;
            TreeSize++;

            treeCopy(y->L, x->L, NIL1, NIL2, y);
            treeCopy(y->R, x->R, NIL1, NIL2, y);
        }
    }

    
    void destruct(NP Node) {
        if (Node != NIL) {
            destruct(Node->L);
            destruct(Node->R);
            if (Node != nullptr) {
                delete Node;
            }
        }
    }
    ~RBTree(){ //destructor
		destruct(root);
	}

    
    void InsertFixer(NP z) { //Rearranges order of tree
        NP y; //Should be the uncle
        while (z->P->isRed == 1) {
            // prettyPrint();
            // cout << endl;
            if (z->P == z->P->P->L) {
                y = z->P->P->R; //set Uncle to right child
    
                if (y->isRed == 1) { //case 1 red Uncle
                    z->P->isRed = 0;
                    y->isRed = 0;
                    z->P->P->isRed = 1;
                    z = z->P->P;
                }
                else {
                    if (z == z->P->R) {
                        z = z->P;
                        leftRotate(z); 
                    }
    
                    z->P->isRed = 0;
                    z->P->P->isRed = 1;
                    rightRotate(z->P->P);
                }
            }
    
            else {
                y = z->P->P->L; //set uncle
                
                if (y->isRed == 1) {
                    y->isRed = 0;
                    z->P->isRed = 0;
                    z->P->P->isRed = 1;
                    z = z->P->P;
                }
    
                else {
                    if (z == z->P->L) {
                        z = z->P;
                        rightRotate(z); // should this be leftrotate
                    }
    
                    z->P->isRed = 0;
                    z->P->P->isRed = 1;
                    leftRotate(z->P->P); // should this be right rotate
                }
            }
            if (z == root) { // Do I need this
                break;
            }
            // prettyPrint();
        }
        root->isRed = 0; //make sure root is black
    } //WORKING
    void insert(keytype k, valuetype v) {
        NP z = new node; //creates new current node
        z->P = nullptr; 
        z->info = v;
        z->key = k;
        z->L = NIL;
        z->R = NIL;
        z->P = nullptr;
        z->isRed = 1; 
        z->size = 1;
    
        NP y = nullptr; 
        NP x = this->root; 
    
        while(x != NIL) {
            y = x;
            y->size++;
            if (z->key < x->key) {
                x = x->L;
            }
            else {
                x = x->R;
            }
        } 
    
        z->P = y; //sets y as parent node
        if (y == nullptr) {
            root = z; // changed root to this->root
        }
        else if (z->key < y->key) {
            y->L = z;
        }
        else {
            y->R = z;
        }

        /*Everything past this line is different from the book*/
        if (z->P == nullptr) { //checks for root node
            z->isRed = 0;
            return;
        }
    
        if (z->P->P == nullptr) { //checks grandparent
            return;
        }
    
        //Rearrange tree
        InsertFixer(z); 
        // prettyPrint();
        // cout << endl;
    } //WORKING

    
    int remove(keytype k) { //deletes node replace w Succ
        bool finished = true;
        return removeHelp(this->root, k);
        if (finished) {
            return 1;
        }
        else {
            return 0;
        }
    }
    int removeHelp(NP Node, keytype find) {
        //first search for key
        NP z = NIL; //
        NP x, y;
        TreeSize--;

        

        while (Node != NIL) {
            Node->size--;
            if (Node->key == find) {
                //found node with key
                z = Node;
            }
    
            if(Node->key <= find) {
                //key is bigger than the nodes
                Node = Node->R;
            }
            else {
                //key is smaller than the nodes
                Node = Node->L;

            }
        } 
    
        if (z == NIL) { //Key not in tree
            TreeSize++;
            return 0;
        }
    
        y = z;
        int yTempColor = y->isRed;


        if (z->L == NIL) {
            x = z->R;
            removeHelp2(z, z->R); 
        }
        else if (z->R == NIL) {
            x = z->L;
            removeHelp2(z, z->L); 
        }
        else {
            y = maximum(z->L); 
            yTempColor = y->isRed;
            x = y->L;
            if (y->P == z) {
                x->P = y;
            }
            else {
                removeHelp2(y, y->L); 
                y->L = z->L;
                y->L->P = y;
            }
    
            removeHelp2(z, y); 
            y->R = z->R;
            y->R->P = y;
            y->isRed = z->isRed;
        }
    
        delete z;
        if (yTempColor == 0) {
            fixDelete(x); 
        }
        return 1;
    }
    void removeHelp2(NP u, NP v) { // what does this function do
        if (u->P == nullptr) {
            root = v;
        }
        else if (u == u->P->L) {
            u->P->L = v;
        }
        else {
            u->P->R = v;
        }
        v->P = u->P;
    }
    void fixDelete(NP x) {
        NP y;
        while (x != root && x->isRed == 0) {
            if (x == x->P->L) {
                y = x->P->R;
                if (y->isRed == 1) {
                    y->isRed = 0;
                    x->P->isRed = 1;
                    leftRotate(x->P);
                    y = x->P->R;
                }
    
                if (y->L->isRed == 0 && y->R->isRed == 0)  {
                   y->isRed = 1;
                    x = x->P; 
                }
                else {
                    if (y->R->isRed == 0) {
                        y->L->isRed = 0;
                        y->isRed = 1;
                        rightRotate(y);
                        y = x->P->R;
                    }
    
                    y->isRed = x->P->isRed;
                    x->P->isRed = 0;
                    y->R->isRed = 0;
                    leftRotate(x->P);
                    x = root;
                }
            }
            else {
                y = x->P->L;
                if (y->isRed == 1) {
                    y->isRed = 0;
                    x->P->isRed = 1;
                    rightRotate(x->P);
                    y = x->P->L;
                }
    
                if (y->R->isRed == 0 && y->R->isRed == 0) {
                    y->isRed = 1;
                    x = x->P;
                }
                else {
                    if (y->L->isRed == 0) {
                        y->R->isRed = 0;
                        y->isRed = 1;
                        leftRotate(y);
                        y = x->P->L;
                    }
    
                    y->isRed = x->P->isRed;
                    x->P->isRed = 0;
                    y->L->isRed = 0;
                    rightRotate(x->P);
                    x = root;
                }
            }
        }
    
        x->isRed = 0;
    }

       
    void leftRotate(NP x) { 
        NP y = x->R;
        x->R = y->L;
        if (y->L != NIL) { //left child
            y->L->P = x;
        }
        y->P = x->P;
        if (x->P == nullptr) {
            this->root = y;
        }
        else if (x == x->P->L) {
            x->P->L = y;
        }
        else {
            x->P->R = y;
        }
        y->L = x;
        x->P = y;
        y->size = x->size;
        x->size = x->L->size + x->R->size + 1;
    } //WORKING
    void rightRotate(NP x) {
        NP y = x->L;
        x->L = y->R;
        if (y->R != NIL) {
            y->R->P = x;
        }
        y->P = x->P;
        if (x->P == nullptr) {
            this->root = y;
        }
        else if (x == x->P->R) {
            x->P->R = y;
        }
        else {
            x->P->L = y;
        }
        y->R = x;
        x->P = y;
        y->size = x->size;
        x->size = x->L->size + x->R->size + 1;
    } //WORKING



    valuetype * searchHelp(NP x, keytype k) {
        if (k == x->key || x == NIL) {
            valuetype *p = &x->info;
            return p;
        }

        if (k < x->key) {
            return searchHelp(x->L, k);
        }
        else {
            return searchHelp(x->R, k);
        }

        return NULL;
    }
    valuetype * search(keytype k) { //returns a pointer to valuetype stored with key
        return searchHelp(this->root, k);
    }
    
    NP maximum(NP Node) { //get maximum key
        while (Node->R != NIL) {
            Node = Node->R;
        }
        return Node;
    }
    NP minimum(NP Node) { //get minimum key
        while (Node->L != NIL) {
            Node = Node->L;
        }
        return Node;
    }


    int size() {  //returns number of nodes in tree
        return this->TreeSize;
    }

    int rank(keytype k) {
        NP x = findNode(root, k);
        if (x == NIL) {
            return 0;
        }

        int w = x->L->size + 1;
        NP y = x;

        while (y != root) {
            if (y == y->P->R) {
                w = w + y->P->L->size + 1;
            }
            y = y->P;
        }
        return w;
    }
    NP findNode(NP Node, keytype x) {
        if (Node == NIL || Node->key == x) {
            return Node;
        }

        if (x < Node->key) {
            return findNode(Node->L, x);
        }

        return findNode(Node->R, x);
        return NULL; //No key found
    }

    keytype select(int pos) {
        NP Node = locater(this->root, pos);
        return Node->key;
    }
    NP locater(NP Node, int k) {
        int rp = Node->L->size + 1;
        if (rp == k) {
            
            return Node;
        }
        else if (k < rp) {
            
            return locater(Node->L, k);
        }
        else {
             
            return locater(Node->R, k - rp);
        }
    }

    void printk(int k) {
        selectRank = 0;
        Ker(this->root, k);
        cout << endl;
    }
    void Ker(NP Node, int k) {
        if (k > selectRank && Node != NIL) {
            Ker(Node->L, k);
            if (selectRank < k) {
                cout << Node->key << " ";
                selectRank++;
            }
            Ker(Node->R, k);
        }
    }

    
    void postorder() { //post order traversal
        postOrderHelper(this->root);
        cout << endl;
    }
    void postOrderHelper(NP Node) {
        if(Node != NIL) {
            postOrderHelper(Node->L);
            postOrderHelper(Node->R);
            cout << Node->key << " ";
        }
    }

    keytype *successor(keytype k) { //returns pointer to succ
        NP Node = findNode(this->root, k);
        if (Node->R != NIL) {
            NP x = minimum(Node->R);
            keytype *temptemp = &x->key;
            return temptemp;
        }
        
        NP y = Node->P;
        while (y != NIL && Node == y->R) {
            Node = y; // is this suppose to be key
            y = y->P;
        }
        keytype *temptemp2 = &y->key;
        return temptemp2; 
    }
    keytype *predecessor(keytype k) { //returns pointer to pred
        NP Node = findNode(this->root, k);
        if (Node->L != NIL) {
            NP x = maximum(Node->L);
            keytype *temptemp = &x->key;
            return temptemp;
        }
        
        NP y = Node->P;
        while (y != NIL && Node == y->L) {
            Node = y; 
            y = y->P;
        }
        keytype *temptemp2 = &y->key;
        return temptemp2; 
    }

    void inorder() {
        inorderHelper(this->root);
        cout << endl;
    }
    void inorderHelper(NP Node) {
        if (Node != NIL) {
            inorderHelper(Node->L);
            cout << Node->key << " ";
            inorderHelper(Node->R);
        }
    }

    void preorder() {
        preorderHelper(this->root);
        cout << endl;
    }
    void preorderHelper(NP Node) {
        if (Node != NIL) {
            cout << Node->key << " ";
            preorderHelper(Node->L);
            preorderHelper(Node->R);
        }
    }
};



